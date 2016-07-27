package com.github.invictum.panels.strategy;

import com.github.invictum.pages.AbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoWaitStrategy implements PanelInitStrategy {

    public static final Logger LOG = LoggerFactory.getLogger(NoWaitStrategy.class);

    @Override
    public void apply(AbstractPage context) {
        LOG.debug("Invoked {} wait strategy", this);
        LOG.debug("No any wait applied.");
    }
}
