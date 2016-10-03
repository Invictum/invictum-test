package com.github.invictum.tricks.core;

import com.github.invictum.pages.AbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrickFactory {

    private final static Logger LOG = LoggerFactory.getLogger(TrickFactory.class);

    public static <T extends AbstractTrick> T getTrick(Class<T> trickClass, AbstractPage context) {
        T trick;
        try {
            trick = trickClass.newInstance();
            trick.setContext(context);
        } catch (ReflectiveOperationException e) {
            LOG.error("Failed to init {} trick", trickClass);
            throw new IllegalStateException(e.getMessage());
        }
        return trick;
    }
}
