package com.github.invictum.unified.data.provider;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.unified.data.provider.parsers.Parser;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.github.invictum.utils.properties.EnhancedSystemProperty.DataProviderParser;

public class UnifiedDataProviderFactory {

    private static Map<Class, UnifiedDataProvider> locatorProviders = new HashMap<>();
    private static Parser parser = null;
    private final static Logger LOG = LoggerFactory.getLogger(UnifiedDataProviderFactory.class);

    private UnifiedDataProviderFactory() {
        // disable constructor.
    }

    static {
        String parserClassName = PropertiesUtil.getProperty(DataProviderParser);
        if (StringUtils.equals(DataProviderParser.defaultValue(), parserClassName)) {
            LOG.warn("Default unified data provider parser is used '{}'. You may configure it via '{}' property",
                    parserClassName, DataProviderParser);
        }
        try {
            parser = (Parser) Class.forName(parserClassName).newInstance();
            LOG.debug("Used '{}' unified data provider parser", parserClassName);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(String.format("Unable to init '%s' parser", parserClassName));
        }
    }

    private static Map<String, String> mergeLocators(Class relatedClass, Class rootClass) {
        Map<String, String> locators = new HashMap<>();
        while (relatedClass != rootClass) {
            Map<String, String> currentLocators = parser.load(relatedClass.getSimpleName()).getLocators();
            currentLocators.putAll(locators);
            locators = currentLocators;
            relatedClass = relatedClass.getSuperclass();
        }
        return locators;
    }

    public static UnifiedDataProvider getInstance(final Object relatedObject) {
        return getInstance(relatedObject.getClass());
    }

    public static UnifiedDataProvider getInstance(final Class relatedClass) {
        if (!locatorProviders.containsKey(relatedClass)) {
            UnifiedDataProvider dataProvider = parser.load(relatedClass.getSimpleName());
            if (AbstractPage.class.isAssignableFrom(relatedClass)) {
                dataProvider.setLocators(mergeLocators(relatedClass, AbstractPage.class));
            }
            if (AbstractPanel.class.isAssignableFrom(relatedClass)) {
                dataProvider.setLocators(mergeLocators(relatedClass, AbstractPanel.class));
            }
            dataProvider.setRelatedClassName(relatedClass.getSimpleName());
            locatorProviders.put(relatedClass, dataProvider);
            LOG.debug("Loaded markup for {} with {} description", relatedClass.getSimpleName(), dataProvider.getName());
        }
        LOG.debug("Using markup for {}", relatedClass.getSimpleName());
        return locatorProviders.get(relatedClass);
    }
}
