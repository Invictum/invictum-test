package com.github.invictum.utils.properties;

import net.serenitybdd.core.SerenitySystemProperties;
import net.thucydides.core.ThucydidesSystemProperty;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import com.github.invictum.Log;

public class PropertiesUtil {

    private static CompositeConfiguration config = new CompositeConfiguration();

    static {
        String fileName = SerenitySystemProperties.getProperties()
                .getValue(ThucydidesSystemProperty.PROPERTIES, "serenity.properties");
        config.addConfiguration(new SystemConfiguration());
        config.addConfiguration(initPropertiesFromFile(fileName));
    }

    private PropertiesUtil() {
        //disable constructor
    }

    private static PropertiesConfiguration initPropertiesFromFile(String propertyFileName) {
        PropertiesConfiguration properties = new PropertiesConfiguration();
        try {
            properties.load(propertyFileName);
        } catch (ConfigurationException e) {
            Log.error("Failed to load property file.");
        }
        return properties;
    }

    public static String getProperty(String key) {
        return config.getString(key);
    }

    public static String getProperty(EnhancedSystemProperty property) {
        return config.getString(property.toString(), property.defaultValue());
    }
}
