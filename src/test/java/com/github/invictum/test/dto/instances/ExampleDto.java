package com.github.invictum.test.dto.instances;

import com.github.invictum.dto.AbstractDto;
import com.github.invictum.dto.annotation.DtoAttribute;

public class ExampleDto extends AbstractDto {

    @DtoAttribute
    private String textValue;

    @DtoAttribute
    private Boolean boolValue;

    @DtoAttribute
    private Integer intValue;

    @DtoAttribute
    private ExampleDto dto;

    @DtoAttribute
    private EnumAttribute enumValue;

    public ExampleDto getDto() {
        return dto;
    }

    public void setDto(ExampleDto dto) {
        this.dto = dto;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public EnumAttribute getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(EnumAttribute enumValue) {
        this.enumValue = enumValue;
    }
}