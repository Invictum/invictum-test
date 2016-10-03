package com.github.invictum.unified.data.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UnifiedDataProviderUtil {

    private final static Logger LOG = LoggerFactory.getLogger(UnifiedDataProviderUtil.class);

    public static String getDataByKey(final String key, final UnifiedDataProvider dataProvider) {
        Map<String, String> data = dataProvider.getData();
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Data for '%s' key is not found in scope of %s", key,
                    dataProvider.getRelatedClassName()));
        }
        LOG.debug("Used \"{}\" data for \"{}\" key", data.get(key), key);
        return data.get(key);
    }

    public static String getLocatorByKey(final String key, final UnifiedDataProvider dataProvider) {
        Map<String, String> locators = dataProvider.getLocators();
        if (!locators.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Locator for '%s' key is not found in scope of %s", key,
                    dataProvider.getRelatedClassName()));
        }
        LOG.debug("Used \"{}\" locator for \"{}\" key", locators.get(key), key);
        return locators.get(key);
    }
}
