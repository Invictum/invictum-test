package com.github.invictum;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.config.SessionConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImpexImporter {

    private static final int STEP_TIMEOUT = 5000;
    public static int importTimeout;
    private static String username;
    private static String password;

    private static RequestSpecification request = null;
    private static StopWatch timer = new StopWatch();

    private final static Logger LOG = LoggerFactory.getLogger(ImpexImporter.class);

    private ImpexImporter() {
    }

    public static void setParameters(ImporterProperties properties) {
        username = properties.getUser();
        password = properties.getPassword();
        importTimeout = properties.getImportTimeout();
        buildBaseRequest(properties.getSessionKey(), properties.getUrl());
    }

    @Deprecated
    public static void setCredentials(String user, String pass) {
        username = user;
        password = pass;
    }

    @Deprecated
    public static void setParameters(String url, String sessionKey) {
        Pattern pattern = Pattern.compile("(http(s|)://.+?)(/|$)");
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            throw new IllegalStateException("Failed to extract URL for import");
        }
        String uri = String.format("%s/hac", matcher.group(1));
        LOG.info("Using {} URI for impex upload", uri);
        LOG.debug("Using {} key for session", sessionKey);
        buildBaseRequest(sessionKey, uri);
    }

    private static void buildBaseRequest(String sessionKey, String uri) {
        request = new RequestSpecBuilder().setConfig(
                RestAssuredConfig.newConfig().sessionConfig(new SessionConfig().sessionIdName(sessionKey))
                        .sslConfig(new SSLConfig().relaxedHTTPSValidation())).setBaseUri(uri).build();
    }

    private static void makeAuthorizationRequest() {
        LOG.info("Make authorization request");
        Response response = RestAssured.given(request).get("/login.jsp");
        String csrf = response.body().htmlPath().getString("html.head.meta.find { it.@name == '_csrf' }.@content");
        LOG.debug("CSRF token for authorization is {}", csrf);
        request.sessionId(response.getSessionId() == null ? "no-session" : response.getSessionId());
        response = RestAssured.given(request).contentType(ContentType.URLENC).formParameter("_csrf", csrf)
                .formParameter("j_username", username).formParameter("j_password", password)
                .post("/j_spring_security_check");
        request.sessionId(response.getSessionId() == null ? "no-session" : response.getSessionId());
        LOG.debug("Authorized session is {}", response.getSessionId());
    }

    private static void waitABit(long milliseconds) {
        try {
            LOG.debug("Wait {} milliseconds and retry", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOG.error("Import process was interrupted. {}", e.getLocalizedMessage());
        }
    }

    public static void runImport(String impexString) {
        LOG.info("Make import request");
        Response response = RestAssured.given(request).get("/console/impex/import");
        String _csrf = response.body().htmlPath().getString("html.head.meta.find { it.@name == '_csrf' }.@content");
        RequestSpecification importRequest = new RequestSpecBuilder().addRequestSpecification(request)
                .addParam("_csrf", _csrf).addParam("scriptContent", impexString)
                .addParam("validationEnum", "IMPORT_STRICT").addParam("maxThreads", "1").addParam("encoding", "UTF-8")
                .addParam("_legacyMode", "on").addParam("enableCodeExecution", "true")
                .addParam("_enableCodeExecution", "on").build();
        timer.start();
        while (response.statusCode() != 200) {
            response = RestAssured.given(importRequest).post("/console/impex/import");
            LOG.debug(response.statusLine());
            if (response.statusCode() == 403) {
                makeAuthorizationRequest();
            } else {
                waitABit(STEP_TIMEOUT);
            }
            if (timer.getTime() > importTimeout) {
                timer.reset();
                throw new IllegalStateException(
                        String.format("Failed to perform import more than %d ms", importTimeout));
            }
        }
        LOG.info("Import done. It took {} ms", timer.getTime());
        timer.reset();
    }
}
