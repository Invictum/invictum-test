package com.github.invictum.dto.attribute.converter.converters;

import com.github.invictum.dto.attribute.converter.Converter;

public abstract class PrimitiveConverter implements Converter {

    @Override
    public abstract boolean isTypeCompatible(Object object);

    @Override
    public String convert(Object object) {
        return String.valueOf(object);
    }
}
