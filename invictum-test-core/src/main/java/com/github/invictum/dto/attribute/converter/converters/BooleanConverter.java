package com.github.invictum.dto.attribute.converter.converters;

public class BooleanConverter extends PrimitiveConverter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object instanceof Boolean;
    }
}
