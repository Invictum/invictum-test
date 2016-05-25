package org.unified.test.tricks.core;

import org.unified.test.Log;
import org.unified.test.pages.AbstractPage;

public class TrickFactory {
    public static <T extends AbstractTrick> T getTrick(Class<T> trickClass, AbstractPage context) {
        T trick = null;
        try {
            trick = trickClass.newInstance();
            trick.setContext(context);
        } catch (ReflectiveOperationException e) {
            Log.error("Failed to init {} trick", trickClass);
        }
        return trick;
    }
}
