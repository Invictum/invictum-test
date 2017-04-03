package com.github.invictum.utils.properties;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public enum EnhancedSystemProperty {

    LocatorsDirectory("invictum.locators.directory", "locators"),
    SchemasDirectory("invictum.rest.schemas.directory", "rest/schemas"),
    FullDtoView("invictum.full.dto.view", "false"),
    PagesPackageName("invictum.pages.package.name", "."),
    PanelsPackageName("invictum.panels.package.name", "."),
    FixturesPackageName("invictum.fixtures.package.name", "."),
    DefaultUrlKey("invictum.default.url.key", "default"),
    DataProviderParser("invictum.data.provider.parser", "com.github.invictum.unified.data.provider.parsers.YamlParser"),
    PanelInitStrategy("invictum.panel.init.strategy", "com.github.invictum.panels.strategy.NoWaitStrategy"),
    AllureEnvironmentCustomProperties("allure.environment.custom.properties", EMPTY);

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
