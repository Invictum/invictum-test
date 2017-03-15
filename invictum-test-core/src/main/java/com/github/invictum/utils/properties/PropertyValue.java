package com.github.invictum.utils.properties;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PropertyValue {

    private String propertyValue;

    public PropertyValue(String value) {
        propertyValue = value;
    }

    public Integer asInteger() {
        try {
            return Integer.valueOf(propertyValue);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Boolean asBoolean() {
        return Boolean.valueOf(propertyValue);
    }

    public String[] asArray() {
        List<String> result = new ArrayList<>();
        for (String item : propertyValue.split(",")) {
            result.add(StringUtils.trim(item));
        }
        return result.toArray(new String[result.size()]);
    }
}
