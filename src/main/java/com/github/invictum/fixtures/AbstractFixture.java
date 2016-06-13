package com.github.invictum.fixtures;

import com.github.invictum.Log;

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
        String paramSting = "";
        if (params != null) {
            for (String param : params) {
                paramSting += String.format("%s ", param);
            }
        }
        paramSting = paramSting.trim();
        return String.format("%s {%s}", getClass().getSimpleName(), paramSting);
    }

    protected String getParam(int paramIndex) {
        return params.get(paramIndex);
    }
}
