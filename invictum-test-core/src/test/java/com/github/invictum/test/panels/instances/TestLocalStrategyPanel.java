package com.github.invictum.test.panels.instances;

import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.init.WhenPanelInitializes;

public class TestLocalStrategyPanel extends AbstractPanel {

    @WhenPanelInitializes
    public void localStrategy() {
        /** Marker method. */
        parentPage.resetImplicitTimeout();
    }
}
