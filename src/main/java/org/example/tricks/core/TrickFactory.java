package org.example.tricks.core;

import org.example.Log;
import org.example.pages.AbstractPage;

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
