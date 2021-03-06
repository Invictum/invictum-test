package com.github.invictum.tricks.core;

import com.github.invictum.pages.AbstractPage;

public class AbstractTrick {

    private AbstractPage context;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void setContext(final AbstractPage context) {
        this.context = context;
    }

    protected AbstractPage context() {
        return context;
    }
}
