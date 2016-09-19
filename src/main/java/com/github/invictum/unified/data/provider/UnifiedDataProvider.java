package com.github.invictum.unified.data.provider;

import java.util.LinkedHashMap;
import java.util.Map;

public class UnifiedDataProvider {
    private String name;
    private String base;
    private String relatedClassName;
    private Map<String, String> urls = new LinkedHashMap<>();
    private Map<String, String> locators = new LinkedHashMap<>();
    private Map<String, String> data = new LinkedHashMap<>();

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLocators() {
        return locators;
    }

    public void setLocators(Map<String, String> locators) {
        this.locators = locators;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }

    public String getRelatedClassName() {
        return relatedClassName;
    }

    public void setRelatedClassName(String relatedClassName) {
        this.relatedClassName = relatedClassName;
    }
}
