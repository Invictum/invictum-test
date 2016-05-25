package org.example.fixtures;

import org.example.Log;
import org.example.utils.properties.PropertiesUtil;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.*;

import static org.example.utils.properties.EnhancedSystemProperty.FixturesPackageName;

public class FixtureProcessor {

    public static final String FIXTURES_PACKAGE = PropertiesUtil.getProperty(FixturesPackageName);

    private static Set<Class<? extends AbstractFixture>> availableFixtureClasses = new HashSet<>();
    private static Queue<Fixture> registeredFixtures = new ArrayDeque<>();

    static {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(FIXTURES_PACKAGE));
        availableFixtureClasses = reflections.getSubTypesOf(AbstractFixture.class);
        Log.debug("Found {} available fixtures", availableFixtureClasses.size());
    }

    public static void apply(Map<String, String> annotations) {
        for (Class<? extends Fixture> fixtureClass : availableFixtureClasses) {
            for (Map.Entry<String, String> annotation : annotations.entrySet()) {
                if (annotation.getKey().equalsIgnoreCase(fixtureClass.getSimpleName())) {
                    try {
                        Fixture fixture = fixtureClass.newInstance();
                        fixture.setParams(prepareParams(annotation.getValue()));
                        Log.info("Applying {} fixture", fixture);
                        fixture.prepareCondition();
                        registeredFixtures.add(fixture);
                    } catch (ReflectiveOperationException e) {
                        Log.error("Failed to apply {} fixture", fixtureClass);
                    }
                }
            }
        }
    }

    public static void rollback() {
        while (registeredFixtures.size() > 0) {
            Fixture fixture = registeredFixtures.poll();
            Log.info("Rollback for {} fixture", fixture);
            fixture.rollbackCondition();
        }
    }

    private static String[] prepareParams(String paramsString) {
        String[] params = paramsString.split(",");
        int index = 0;
        for (String paramItem : params) {
            params[index] = paramItem.trim();
            index++;
        }
        return params;
    }
}
