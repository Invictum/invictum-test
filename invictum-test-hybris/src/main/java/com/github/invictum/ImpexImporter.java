package com.github.invictum;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.SessionConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImpexImporter {

    public static final int MAX_ATTEMPTS = 5;
    public static final int TIMEOUT = 5000;
    private static String username;
    private static String password;

    private static RequestSpecification request = null;
    private static int attempt;

    private final static Logger LOG = LoggerFactory.getLogger(ImpexImporter.class);

    static {
        attempt = 0;
    }

    public static void setCredentials(String user, String pass) {
        username = user;
        password = pass;
    }

    public static void setParameters(String url, String sessionKey) {
        Pattern pattern = Pattern.compile("(http(s|)://.+?)(/|$)");
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            throw new IllegalStateException("Failed to extract URL for import");
        }
        String uri = String.format("%s/hac", matcher.group(1));
        LOG.info("Using {} URI for impex upload", uri);
        LOG.debug("Using {} key for session", sessionKey);
        request = new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.newConfig().sessionConfig(new SessionConfig().sessionIdName(sessionKey)))
                .setBaseUri(uri).build();
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
            e.printStackTrace();
        }
    }

    public static void runImport(String impexString) {
        LOG.info("Make import request");
        Response response = RestAssured.given(request).get("/console/impex/import");
        String _csrf = response.body().htmlPath().getString("html.head.meta.find { it.@name == '_csrf' }.@content");
        response = RestAssured.given(request).param("_csrf", _csrf).param("scriptContent", impexString)
                .param("validationEnum", "IMPORT_STRICT").param("maxThreads", "1").param("encoding", "UTF-8")
                .param("_legacyMode", "on").param("enableCodeExecution", "true").param("_enableCodeExecution", "on")
                .when().post("/console/impex/import");
        if (attempt == MAX_ATTEMPTS) {
            attempt = 0;
            throw new IllegalStateException(String.format("Failed to authorize during %d attempts", MAX_ATTEMPTS));
        }
        if (response.statusCode() != 200) {
            LOG.debug("Import failed. Try to retry {} of {} attempts", attempt, MAX_ATTEMPTS);
            LOG.debug(response.statusLine());
            attempt++;
            if (response.statusCode() == 403) {
                makeAuthorizationRequest();
            } else {
                waitABit(TIMEOUT);
            }
            runImport(impexString);
        }
        LOG.info("Import done at {} attempt", attempt);
        attempt = 0;
    }
}
