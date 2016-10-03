package com.github.invictum.test.data;

public class TestDto {

    private String string;
    private int integer;

    public TestDto() {
    }

    public TestDto(String string, int integer) {
        this.string = string;
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        TestDto data = (TestDto) obj;
        return (data.getInteger() == this.getInteger()) && (data.getString().equals(this.getString()));
    }

    @Override
    public String toString() {
        return String.format("{string: %s, integer: %s}", string, integer);
    }
}
