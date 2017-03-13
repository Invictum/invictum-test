package com.github.invictum.allure.annotation;

public enum TestFramework {
    JUNIT,
    BDD;


    @Override
    public String toString() {
        return name();
    }
}
