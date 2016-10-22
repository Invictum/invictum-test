package com.github.invictum.locator.factory;

public class LocatorFactory {

    private LocatorFactory() {
        //disable constructor.
    }

    public static boolean isXpath(String xpathCandidate) {
        return xpathCandidate.matches("^(html/|(.|)/).+$");
    }
}
