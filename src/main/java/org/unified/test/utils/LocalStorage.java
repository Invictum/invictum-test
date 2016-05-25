package org.unified.test.utils;

import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.core.webdriver.javascript.JavascriptExecutorFacade;

public class LocalStorage {
    private static JavascriptExecutorFacade jsExecutor = null;

    private static JavascriptExecutorFacade js() {
        if (jsExecutor == null) {
            jsExecutor = new JavascriptExecutorFacade(ThucydidesWebDriverSupport.getDriver());
        }
        return jsExecutor;
    }

    public static void removeItem(String key) {
        js().executeScript(String.format("window.localStorage.removeItem('%s');", key));
    }

    public static boolean isItemPresent(String key) {
        return !(js().executeScript(String.format("return window.localStorage.getItem('%s');", key)) == null);
    }

    public static String getItem(String key) {
        return (String) js().executeScript(String.format("return window.localStorage.getItem('%s');", key));
    }

    public static String getKey(int key) {
        return (String) js().executeScript(String.format("return window.localStorage.key('%s');", key));
    }

    public static Long length() {
        return (Long) js().executeScript("return window.localStorage.length;");
    }

    public static void setItem(String item, String value) {
        js().executeScript(String.format("window.localStorage.setItem('%s','%s');", item, value));
    }

    public static void clear() {
        js().executeScript("window.localStorage.clear();");
    }
}
