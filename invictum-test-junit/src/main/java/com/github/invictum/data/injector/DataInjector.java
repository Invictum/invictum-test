package com.github.invictum.data.injector;

import com.github.invictum.velocity.VelocityProcessor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
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
            /** Wrap yaml documents into list og generic types */
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
        URL file = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if (file == null) {
            throw new IllegalStateException(String.format("Undiscovered resource for %s", resourcePath));
        }
        File resourceFile = new File(file.getFile());
        try {
            String content = FileUtils.readFileToString(resourceFile);
            if (field.getAnnotation(TestData.class).velocity()) {
                LOG.debug("Velocity is enabled, try to process loaded YAML file.");
                return VelocityProcessor.processContent(content);
            }
            return content;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Failed to read %s file", resourceFile.getPath()));
        }
    }
}
