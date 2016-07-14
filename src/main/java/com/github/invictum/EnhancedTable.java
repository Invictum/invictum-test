package com.github.invictum;

import org.jbehave.core.annotations.Parameter;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.Parameters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnhancedTable extends ExamplesTable {

    public EnhancedTable(String tableAsString) {
        super(tableAsString);
    }

    public EnhancedTable(String tableAsString, String headerSeparator, String valueSeparator) {
        super(tableAsString, headerSeparator, valueSeparator);
    }

    public EnhancedTable(String tableAsString, String headerSeparator, String valueSeparator, String ignorableSeparator,
            ParameterConverters parameterConverters) {
        super(tableAsString, headerSeparator, valueSeparator, ignorableSeparator, parameterConverters);
    }

    public EnhancedTable(String tableAsString, String headerSeparator, String valueSeparator, String ignorableSeparator,
            ParameterConverters parameterConverters, TableTransformers tableTransformers) {
        super(tableAsString, headerSeparator, valueSeparator, ignorableSeparator, parameterConverters,
                tableTransformers);
    }

    public <T> List<T> getRowsAs(Class<T> type, boolean replaceNamedParameters) {
        List<T> rows = new ArrayList<T>();
        for (Parameters parameters : getRowsAsParameters(replaceNamedParameters)) {
            rows.add(mapToType(parameters, type, new HashMap<String, String>()));
        }
        return rows;
    }

    public <T> T getFirstRowsAs(Class<T> type, boolean replaceNamedParameters) {
        return getRowsAs(type, replaceNamedParameters).get(0);
    }

    public <T> T getFirstRowsAs(Class<T> type) {
        return getRowsAs(type, false).get(0);
    }

    private <T> T mapToType(Parameters parameters, Class<T> type, Map<String, String> fieldNameMapping) {
        try {
            T instance = type.newInstance();
            Map<String, String> values = parameters.values();
            for (String name : values.keySet()) {
                Field field = findField(type, name, fieldNameMapping);
                Class<?> fieldType = (Class<?>) field.getGenericType();
                Object value = parameters.valueAs(name, fieldType);
                field.setAccessible(true);
                field.set(instance, value);
            }
            return instance;
        } catch (Exception e) {
            throw new ParametersNotMappableToType(parameters, type, e);
        }
    }

    private <T> Field findField(Class<T> type, String name, Map<String, String> fieldNameMapping)
            throws NoSuchFieldException {
        String fieldName = fieldNameMapping.get(name);
        if (fieldName == null) {
            fieldName = name;
        }
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                Parameter parameter = field.getAnnotation(Parameter.class);
                if (fieldName.equals(parameter.name())) {
                    return field;
                }
            }
        }
        return type.getDeclaredField(fieldName);
    }
}
