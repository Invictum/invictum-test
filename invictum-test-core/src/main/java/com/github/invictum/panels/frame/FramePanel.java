package com.github.invictum.panels.frame;

import com.github.invictum.locator.factory.LocatorFactory;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

public class FramePanel extends AbstractPanel {

    @Override
    public WebElementFacade findBy(String elementLocator) {
        return parentPage.findBy(elementLocator);
    }

    @Override
    public WebElementFacade findBy(By by) {
        return parentPage.find(by);
    }

    @Override
    public List<WebElementFacade> findAll(String elementLocator) {
        return parentPage.findAll(elementLocator);
    }

    @Override
    public List<WebElementFacade> findAll(By by) {
        return parentPage.findAll(by);
    }

    @Override
    protected String locatorValue(String locatorKey) {
        return UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        throw new IllegalStateException("Send keys method isn't allowed for frame panels");
    }

    @Override
    public WebElementFacade asWebElement() {
        By locator = LocatorFactory.build(dataProvider.getBase());
        return parentPage.find(locator);
    }
}
