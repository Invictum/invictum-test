package com.github.invictum.data.injector;

import com.github.invictum.utils.ResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class DataInjector {

    private static final Logger LOG = LoggerFactory.getLogger(DataInjector.class);

    public static void injectInto(Object objectToInject) {
        for (Field field : objectToInject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(TestData.class)) {
                injectIntoField(field, objectToInject);
            }
        }
    }

    private static void injectIntoField(Field field, Object target) {
        LOG.debug("Trying to inject data into {} property of {} class", field.getName(),
                target.getClass().getSimpleName());
        if (List.class.isAssignableFrom(field.getType())) {
            /** Load and inject list of dto (pojo) */
            Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            LOG.debug("List with {} generic type detected", genericType.getSimpleName());
            /** Wrap yml documents into list og generic types */
            Yaml yaml = new Yaml(new Constructor(genericType));
            List<Object> data = new ArrayList<>();
            for (Object document : yaml.loadAll(getRelatedContent(field))) {
                data.add(genericType.cast(document));
            }
            setFieldValue(field, target, data);
        } else {
            /** Load and inject regular dto (pojo) */
            Yaml yaml = new Yaml(new Constructor(field.getType()));
            Object data = yaml.load(getRelatedContent(field));
            setFieldValue(field, target, data);
        }
    }

    private static void setFieldValue(Field field, Object target, Object data) {
        try {
            field.setAccessible(true);
            field.set(target, data);
        } catch (IllegalAccessException e) {
            LOG.error("Failed to set data to {} property of {} class", field.getName(),
                    target.getClass().getSimpleName());
        }
    }

    private static String getRelatedContent(Field field) {
        String resourcePath = field.getAnnotation(TestData.class).value();
        boolean velocityEnabled = field.getAnnotation(TestData.class).velocity();
        return ResourceProvider.getFileContent(resourcePath, velocityEnabled);
    }
}
