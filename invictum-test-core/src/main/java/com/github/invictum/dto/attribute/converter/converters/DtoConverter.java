package com.github.invictum.dto.attribute.converter.converters;

import com.github.invictum.dto.AbstractDto;
import com.github.invictum.dto.attribute.converter.Converter;

public class DtoConverter implements Converter {

    @Override
    public boolean isTypeCompatible(Object object) {
        return object instanceof AbstractDto;
    }

    @Override
    public String convert(Object object) {
        return object.toString();
    }
}
