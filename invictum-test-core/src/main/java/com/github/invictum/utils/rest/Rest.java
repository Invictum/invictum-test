package com.github.invictum.utils.rest;

import com.github.invictum.utils.properties.PropertiesUtil;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;
import java.util.Map;

import static com.github.invictum.utils.properties.EnhancedSystemProperty.ApiEndpointDefault;

public class Rest {

    private static final String DEFAULT_PROFILE = "default";

    private static ThreadLocal<String> baseUri = new ThreadLocal<>();
    private static Map<String, RequestSpecification> specifications = new HashMap<>();

    static {
        baseUri.set(PropertiesUtil.getProperty(ApiEndpointDefault));
    }

    public static RequestSpecification request(String profileName) {
        if (!specifications.containsKey(profileName)) {
            RestProfile profile = ProfileManager.getProfile(profileName);
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.addHeaders(profile.getHeaders()).addCookies(profile.getCookies());
            if (profile.getPath() == null) {
                builder.setBasePath("/");
            } else {
                builder.setBasePath(profile.getPath());
            }
            specifications.put(profileName, builder.build());
        }
        return SerenityRest.given(specifications.get(profileName).baseUri(baseUri.get()));
    }

    public static RequestSpecification request() {
        return request(DEFAULT_PROFILE);
    }

    public static RequestSpecification request(String uriKey, String profileName) {
        RequestSpecification request = request(profileName);
        return request.baseUri(PropertiesUtil.getProperty("api.endpoint." + uriKey));
    }

    public static String getBaseUri() {
        return baseUri.get();
    }

    public static void setBaseUri(String newUri) {
        baseUri.set(newUri);
    }

    public static ValidatableResponse response() {
        return SerenityRest.then();
    }
}
