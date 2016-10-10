package com.github.invictum.utils.properties;

public enum EnhancedSystemProperty {

    LocatorsDirectory("locators.directory", "locators"),
    SchemasDirectory("rest.schemas.directory", "rest/schemas"),
    FullDtoView("full.dto.view", "false"),
    PagesPackageName("pages.package.name", "."),
    PanelsPackageName("panels.package.name", "."),
    FixturesPackageName("fixtures.package.name", "."),
    DefaultUrlKey("default.url.key", "default"),
    DataProviderParser("data.provider.parser", "com.github.invictum.unified.data.provider.parsers.YamlParser"),
    PanelInitStrategy("panel.init.strategy", "com.github.invictum.panels.strategy.NoWaitStrategy");

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
