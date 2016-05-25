package org.unified.test.unified.data.provider;

import org.unified.test.Log;

import java.util.Map;

public class UnifiedDataProviderUtil {
    public static String getDataByKey(final String key, final UnifiedDataProvider dataProvider) {
        Map<String, String> data = dataProvider.getData();
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Data related to '%s' key is not found.", key));
        }
        Log.debug("Used \"{}\" data for \"{}\" key", data.get(key), key);
        return data.get(key);
    }

    public static String getLocatorByKey(final String key, final UnifiedDataProvider dataProvider) {
        Map<String, String> locators = dataProvider.getLocators();
        if (!locators.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Locator for '%s' key is not found.", key));
        }
        Log.debug("Used \"{}\" locator for \"{}\" key", locators.get(key), key);
        return locators.get(key);
    }
}
