package com.github.invictum.dto.attribute.converter.converters;

import com.github.invictum.dto.attribute.converter.Converter;

public class StringConverter implements Converter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object instanceof String;
    }

    @Override
    public String convert(Object object) {
        return (String) object;
    }
}
