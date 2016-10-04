package com.github.invictum.panels;

import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

public class FloatingPanel extends AbstractPanel {

    @Override
    protected String locatorValue(final String locatorKey) {
        return UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
    }

    @Override
    protected By locator(String locatorKey) {
        String locatorValue = locatorValue(locatorKey);
        return isXpath(locatorValue) ? By.xpath(locatorValue) : By.cssSelector(locatorValue);
    }

    @Override
    public WebElementFacade locate(final String locatorKey) {
        return findBy(locator(locatorKey));
    }

    @Override
    public List<WebElementFacade> locateAll(final String locatorKey) {
        return findAll(locator(locatorKey));
    }
}
