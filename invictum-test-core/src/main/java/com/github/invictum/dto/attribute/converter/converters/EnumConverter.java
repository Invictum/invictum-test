package com.github.invictum.dto.attribute.converter.converters;

import com.github.invictum.dto.attribute.converter.Converter;

public class EnumConverter implements Converter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object instanceof Enum;
    }

    @Override
    public String convert(Object object) {
        return ((Enum) object).name();
    }
}
