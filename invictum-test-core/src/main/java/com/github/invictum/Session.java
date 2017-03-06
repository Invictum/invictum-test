package com.github.invictum;

import net.serenitybdd.core.Serenity;

public class Session {

    private Session() {
        //disable constructor
    }

    public static void put(Object key, Object value) {
        Serenity.getCurrentSession().put(key, value);
    }

    public static void putGracefully(Object key, Object value) {
        if (!Serenity.getCurrentSession().containsKey(key)) {
            Serenity.getCurrentSession().put(key, value);
        }
    }

    public static String get(Object key) {
        return (String) Serenity.getCurrentSession().get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(final Object key, final Class<T> valueClass) {
        Object raw = Serenity.getCurrentSession().get(key);
        return (raw != null && raw.getClass().equals(valueClass)) ? (T) raw : null;
    }
}
