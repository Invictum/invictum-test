package com.github.invictum.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.openqa.selenium.By;

import java.util.List;

public class FloatingPanel extends AbstractPanel {

    final static public String BASE_PANEL_LOCATOR = "//body";

    @Override
    public void initWith(final AbstractPage parentPage) {
        super.parentPage = parentPage;
        if (dataProvider.getBase() == null) {
            panel = WebElementFacadeImpl
                    .wrapWebElement(getDriver(), this.parentPage.find(By.xpath(BASE_PANEL_LOCATOR)), timeout);
            return;
        }
        if (isXpath(dataProvider.getBase())) {
            panel = WebElementFacadeImpl
                    .wrapWebElement(getDriver(), this.parentPage.find(By.xpath(dataProvider.getBase())), timeout);
        } else {
            panel = WebElementFacadeImpl
                    .wrapWebElement(getDriver(), this.parentPage.find(By.cssSelector(dataProvider.getBase())), timeout);
        }
    }

    @Override
    protected String locator(final String locatorKey) {
        return UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
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
