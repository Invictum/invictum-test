package com.github.invictum.utils.properties;

public enum EnhancedSystemProperty {

    LocatorsDirectory("locators.directory", "locators"),
    SchemasDirectory("rest.schemas.directory", "rest/schemas"),
    ProfilesDirectory("rest.profiles.directory", "rest/profiles"),
    FullDtoView("full.dto.view", "false"),
    PagesPackageName("pages.package.name", "."),
    PanelsPackageName("panels.package.name", "."),
    FixturesPackageName("fixtures.package.name", "."),
    LayoutExpectedDirectory("layout.expected.directory", "src/test/resources/images/"),
    LayoutResultsDirectory("layout.results.directory", "target/layout/results/"),
    LayoutImageExtension("layout.image.extension", "png"),
    ApiEndpointDefault("api.endpoint.default", "http://localhost:8080");

    private String key;
    private String defaultValue;

    EnhancedSystemProperty(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return key;
    }
}
