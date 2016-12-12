package com.github.invictum.dto.attribute.converter.converters;

public class IntegerConverter extends PrimitiveConverter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object instanceof Integer;
    }
}
