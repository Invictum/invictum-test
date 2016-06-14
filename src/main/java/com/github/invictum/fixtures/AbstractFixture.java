package com.github.invictum.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractFixture implements Fixture {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractFixture.class);
    private List<String> params = null;

    @Override
    public void setParams(String[] params) {
        this.params = Arrays.asList(params);
    }

    @Override
    public void rollbackCondition() {
        LOG.debug("Default rollback for {} fixture", this);
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
