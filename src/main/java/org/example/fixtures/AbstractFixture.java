package org.example.fixtures;

import org.example.Log;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractFixture implements Fixture {

    private List<String> params = null;

    @Override
    public void setParams(String[] params) {
        this.params = Arrays.asList(params);
    }

    @Override
    public void rollbackCondition() {
        Log.debug("Default rollback for {} fixture", this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    protected String getParam(int paramIndex) {
        return params.get(paramIndex);
    }
}
