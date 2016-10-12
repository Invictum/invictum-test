package com.github.invictum;

public class ImporterProperties {

    private static final String DEFAULT_SESSION_KEY = "JSESSIONID";
    private static final int DEFAULT_IMPORT_TIMEOUT = 30000;

    private String user;
    private String password;
    private String url;
    private String sessionKey;
    private int importTimeout;

    public ImporterProperties() {
        sessionKey = ImporterProperties.DEFAULT_SESSION_KEY;
        importTimeout = ImporterProperties.DEFAULT_IMPORT_TIMEOUT;
    }

    public String getUser() {
        return user;
    }

    public ImporterProperties setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ImporterProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ImporterProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public ImporterProperties setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public int getImportTimeout() {
        return importTimeout;
    }

    public ImporterProperties setImportTimeout(int importTimeout) {
        this.importTimeout = importTimeout;
        return this;
    }
}
