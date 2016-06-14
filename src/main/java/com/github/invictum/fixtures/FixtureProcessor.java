package com.github.invictum.fixtures;

import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FixtureProcessor {

    public static final String FIXTURES_PACKAGE = PropertiesUtil
            .getProperty(EnhancedSystemProperty.FixturesPackageName);
    private final static Logger LOG = LoggerFactory.getLogger(FixtureProcessor.class);

    private static Set<Class<? extends AbstractFixture>> availableFixtureClasses = new HashSet<>();
    private static ThreadLocal<Queue<Fixture>> registeredFixtures = new ThreadLocal<Queue<Fixture>>() {
        @Override
        protected Queue<Fixture> initialValue() {
            return new ArrayDeque<>();
        }
    };

    static {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(FIXTURES_PACKAGE));
        availableFixtureClasses = reflections.getSubTypesOf(AbstractFixture.class);
        LOG.debug("Found {} available fixtures", availableFixtureClasses.size());
    }

    public static void apply(Map<String, String> annotations) {
        for (Class<? extends Fixture> fixtureClass : availableFixtureClasses) {
            for (Map.Entry<String, String> annotation : annotations.entrySet()) {
                if (annotation.getKey().equalsIgnoreCase(fixtureClass.getSimpleName())) {
                    try {
                        Fixture fixture = fixtureClass.newInstance();
                        fixture.setParams(prepareParams(annotation.getValue()));
                        LOG.info("Applying {} fixture", fixture);
                        fixture.prepareCondition();
                        registeredFixtures.get().add(fixture);
                    } catch (ReflectiveOperationException e) {
                        LOG.error("Failed to apply {} fixture", fixtureClass);
                    }
                }
            }
        }
    }

    public static void rollback() {
        while (registeredFixtures.get().size() > 0) {
            Fixture fixture = registeredFixtures.get().poll();
            LOG.info("Rollback for {} fixture", fixture);
            fixture.rollbackCondition();
        }
    }

    public static Queue<Fixture> getRegisteredFixtures() {
        return registeredFixtures.get();
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
