package com.github.invictum.dto;

import org.apache.commons.lang3.StringUtils;

public class Attribute {

    private String name;
    private String value;

    public Attribute() {
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{name: %s, value: %s}", name != null ? name : "null", value != null ? value : "null");
    }

    @Override
    public int hashCode() {
        return String.format("%s%s", name, value).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Attribute data = (Attribute) obj;
        return StringUtils.equals(data.getName(), name) && StringUtils.equals(data.getValue(), value);
    }

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
}
