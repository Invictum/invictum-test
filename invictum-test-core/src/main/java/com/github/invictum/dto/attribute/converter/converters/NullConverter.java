package com.github.invictum.dto.attribute.converter.converters;

import com.github.invictum.dto.attribute.converter.Converter;

public class NullConverter implements Converter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object == null;
    }

    @Override
    public String convert(Object object) {
        return null;
    }
}
