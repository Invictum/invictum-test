package com.github.invictum.test.dto;

import com.github.invictum.dto.AbstractDto;
import com.github.invictum.dto.annotation.DtoAttribute;

public class ExampleDto extends AbstractDto {

    @DtoAttribute
    private String name;

    @DtoAttribute
    private String value;

    @DtoAttribute
    private String testValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }
}
