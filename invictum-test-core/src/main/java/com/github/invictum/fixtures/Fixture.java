package com.github.invictum.fixtures;

public interface Fixture {
    void prepareCondition();

    void setParams(String[] params);

    void rollbackCondition();
}
