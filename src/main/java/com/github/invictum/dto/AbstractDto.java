package com.github.invictum.dto;

import com.github.invictum.Log;
import com.github.invictum.dto.annotation.DtoAttribute;
import com.github.invictum.dto.annotation.KeyAttribute;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AbstractDto {

    public static final boolean FULL_DTO_VIEW = Boolean
            .valueOf(PropertiesUtil.getProperty(EnhancedSystemProperty.FullDtoView));

    private String extractData(Object object, Field attribute) {
        attribute.setAccessible(true);
        try {
            return (String) attribute.get(object);
        } catch (IllegalAccessException e) {
            Log.error("Failed to get data for {} attribute", attribute);
        }
        return null;
    }

    private List<Attribute> getData(Object object, boolean includeNulls) {
        List<Attribute> data = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            Attribute attribute = new Attribute();
            if (field.isAnnotationPresent(DtoAttribute.class)) {
                attribute.setName(field.getName());
                attribute.setValue(extractData(object, field));
                if (attribute.getValue() != null || includeNulls) {
                    if (field.isAnnotationPresent(KeyAttribute.class)) {
                        data.add(0, attribute);
                    } else {
                        data.add(attribute);
                    }
                }
            }
        }
        return data;
    }

    @Override
    public String toString() {
        String stringView = "";
        List<Attribute> data = getData(this, FULL_DTO_VIEW);
        if (data.isEmpty()) {
            return "{null}";
        }
        for (Attribute attribute : data) {
            String value = attribute.getValue();
            stringView += String.format("%s: %s, ", attribute.getName(), value == null ? "null" : value);
        }
        return String.format("{%s}", stringView.substring(0, stringView.length() - 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        List<Attribute> expected = getData(this, false);
        List<Attribute> actual = getData(obj, false);
        return actual.containsAll(expected);
    }

    @Override
    public int hashCode() {
        return getData(this, true).hashCode();
    }
}
