package com.github.invictum.utils.rest;

import java.util.LinkedHashMap;
import java.util.Map;

public class RestProfile {

    private String name;
    private String path;
    private Map<String, String> headers = new LinkedHashMap<>();
    private Map<String, String> cookies = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}
