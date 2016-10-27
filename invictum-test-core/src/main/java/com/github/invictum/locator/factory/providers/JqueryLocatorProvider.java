package com.github.invictum.locator.factory.providers;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class JqueryLocatorProvider implements LocatorProvider {

    @Override
    public boolean isAcceptableTo(String locator) {
        return locator.matches("^jquery(.+?|)=.+$");
    }

    @Override
    public By wrap(String locator) {
        locator = StringUtils.replacePattern(locator, "jquery(.+?|)=(\\s{0,}|)", StringUtils.EMPTY).trim();
        return net.serenitybdd.core.annotations.findby.By.jquery(locator);
    }
}
