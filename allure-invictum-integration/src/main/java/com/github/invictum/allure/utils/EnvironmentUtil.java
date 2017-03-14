package com.github.invictum.allure.utils;

import com.github.invictum.utils.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.config.AllureConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentUtil {

    private final static Logger LOG = LoggerFactory.getLogger(EnvironmentUtil.class);
    private final static String FILE = "environment.properties";

    private static File environmentFile = new File(AllureConfig.newInstance().getResultsDirectory()
            .getAbsolutePath() + "/" + FILE);
    private static Properties environmentProperties = new Properties();

    private static void prepareEnvironmentProperties() {
        addProperty("Project", "serenity.project.name");
        addProperty("Browser", "webdriver.driver");
        addProperty("URL", "webdriver.base.url");
    }

    private static void addProperty(String key, String property) {
        String value = PropertiesUtil.getProperty(property);
        if (value != null) {
            environmentProperties.setProperty(key, value);
        }
    }

    private static void write() {
        try {
            FileWriter writer = new FileWriter(environmentFile);
            environmentProperties.store(writer, "Comment");
        } catch (IOException e) {
            LOG.error("Failed to create environment file");
        }
    }

    public static void create() {
        if (!environmentFile.exists()) {
            LOG.debug("Environment doesn't exist, creating it");
            prepareEnvironmentProperties();
            write();
        }
    }
}
