package com.github.invictum.panels.strategy;

import com.github.invictum.pages.AbstractPage;

public interface PanelInitStrategy {
    void apply(final AbstractPage context);
}
