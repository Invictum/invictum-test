package com.github.invictum.panels;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

public class FloatingPanel extends AbstractPanel {

    public FloatingPanel() {
    }

    @Override
    protected String locator(final String locatorKey) {
        return super.keyToLocator(locatorKey);
    }

    @Override
    public WebElementFacade locate(final String locatorKey) {
        String locator = locator(locatorKey);
        if (isXpath(locator)) {
            return findBy(By.xpath(locator));
        }
        return findBy(By.cssSelector(locator));
    }

    @Override
    public List<WebElementFacade> locateAll(final String locatorKey) {
        String locator = locator(locatorKey);
        if (isXpath(locator)) {
            return findAll(By.xpath(locator));
        }
        return findAll(By.cssSelector(locator));
    }

}
