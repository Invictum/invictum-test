package org.unified.test.unified.data.provider;

import org.unified.test.Log;
import org.unified.test.unified.data.provider.parsers.Parser;
import org.unified.test.unified.data.provider.parsers.YamlParser;

import java.util.HashMap;
import java.util.Map;

public class UnifiedDataProviderFactory {

    private static Map<Class, UnifiedDataProvider> locatorProviders = new HashMap<>();
    private static Parser parser = new YamlParser();

    private UnifiedDataProviderFactory() {
        // disable constructor.
    }

    public static UnifiedDataProvider getInstance(final Object relatedObject) {
        Class relatedClass = relatedObject.getClass();
        if (!locatorProviders.containsKey(relatedClass)) {
            UnifiedDataProvider dataProvider = parser.load(relatedClass.getSimpleName());
            locatorProviders.put(relatedClass, dataProvider);
            Log.debug("Loaded markup for {} with {} description", relatedClass.getSimpleName(), dataProvider.getName());
        }
        Log.debug("Using markup for {}", relatedClass.getSimpleName());
        return locatorProviders.get(relatedClass);
    }

    public static void setParser(final Parser parserToSet) {
        parser = parserToSet;
        Log.info("Set parser to {}", parserToSet.getClass().getSimpleName());
    }
}
