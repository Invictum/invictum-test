package com.github.invictum.test.enhanced.table;

public class TestTableDto {

    private String key;
    private String value;

    public TestTableDto() {
    }

    public TestTableDto(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{key: %s, value: %s}", key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        TestTableDto forCompare = (TestTableDto) obj;
        return key.contentEquals(forCompare.key) && value.contentEquals(forCompare.value);
    }
}
