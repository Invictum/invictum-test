package org.example.dto;

import org.example.Log;
import org.example.dto.annotation.DtoAttribute;
import org.example.dto.annotation.KeyAttribute;
import org.example.utils.properties.PropertiesUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.properties.EnhancedSystemProperty.FullDtoView;

public class AbstractDto {

    public static final boolean FULL_DTO_VIEW = Boolean.valueOf(PropertiesUtil.getProperty(FullDtoView));

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
        for (Attribute attribute : getData(this, FULL_DTO_VIEW)) {
            String value = attribute.getValue();
            stringView += String.format("%s: %s, ", attribute.getName(), value == null ? "null" : value);
        }
        if (stringView.isEmpty()) {
            return super.toString();
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
        return toString().hashCode();
    }
}
