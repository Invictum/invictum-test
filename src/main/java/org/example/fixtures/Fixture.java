package org.example.fixtures;

public interface Fixture {
    void prepareCondition();

    void setParams(String[] params);

    void rollbackCondition();
}
