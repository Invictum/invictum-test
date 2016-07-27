package com.github.invictum.panels.strategy;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JqueryWaitStrategy implements PanelInitStrategy {

    public static final Logger LOG = LoggerFactory.getLogger(JqueryWaitStrategy.class);

    @Override
    public void apply(AbstractPage context) {
        LOG.debug("Invoked {} wait strategy", this);
        context.getTrick(Wait.class).waitForJquery();
    }
}
