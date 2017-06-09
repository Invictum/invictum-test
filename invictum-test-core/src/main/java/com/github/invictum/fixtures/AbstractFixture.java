package com.github.invictum.fixtures;

import org.apache.commons.lang3.StringUtils;
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
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append(StringUtils.SPACE);
        if (params != null && params.size() > 0) {
            builder.append("{");
            for (String param : params) {
                builder.append(param).append(StringUtils.SPACE);
            }
            builder.delete(builder.length() - 1, builder.length()).append("}");
        }
        return builder.toString().trim();
    }

    @Deprecated
    protected String getParam(int paramIndex) {
        return ((paramIndex + 1) <= params.size()) ? params.get(paramIndex) : null;
    }

    protected List<String> parameters() {
        return params;
    }
}
