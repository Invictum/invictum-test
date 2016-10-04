package com.github.invictum.panels;

import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.List;

public class FloatingPanel extends AbstractPanel {

    @Override
    protected String locatorValue(final String locatorKey) {
        return UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
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
