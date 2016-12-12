package com.github.invictum.dto.attribute.converter.converters;

public class DoubleConverter extends PrimitiveConverter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object instanceof Double;
    }
}
