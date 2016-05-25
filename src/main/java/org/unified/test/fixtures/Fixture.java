package org.unified.test.fixtures;

public interface Fixture {
    void prepareCondition();

    void setParams(String[] params);

    void rollbackCondition();
}
