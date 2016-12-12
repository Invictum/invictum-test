package com.github.invictum.dto.attribute.converter;

/**
 * Represents a class that able to convert data in DTO annotated with @DtoAttribute to String representation.
 */
public interface Converter {
    boolean isTypeCompatible(Object object);
    String convert(Object object);
}
