package com.github.invictum.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.Visibility;
import com.github.invictum.tricks.Wait;
import com.github.invictum.tricks.core.AbstractTrick;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class AbstractPanel {

    private static final String PANEL_LOCATOR_PREFIX = ".";
    protected WebElementFacade panel;
    protected UnifiedDataProvider dataProvider;
    protected AbstractPage parentPage;

    public AbstractPanel() {
        dataProvider = UnifiedDataProviderFactory.getInstance(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void initWith(final AbstractPage parentPage, final WebElementFacade panelElement) {
        this.parentPage = parentPage;
        this.panel = panelElement;
    }

    protected <T extends AbstractTrick> T getTrick(Class<T> trickClass) {
        return parentPage.getTrick(trickClass);
    }

    protected boolean isXpath(String locator) {
        return parentPage.isXpath(locator);
    }

    public WebElementFacade findBy(final String elementLocator) {
        return panel.findBy(elementLocator);
    }

    public WebElementFacade findBy(By by) {
        return panel.find(by);
    }

    public List<WebElementFacade> findAll(final String elementLocator) {
        return panel.thenFindAll(elementLocator);
    }

    public List<WebElementFacade> findAll(By by) {
        return panel.thenFindAll(by);
    }

    public WebDriver getDriver() {
        return parentPage.getDriver();
    }

    public Alert getAlert() {
        return parentPage.getAlert();
    }

    public Object evaluateJavascript(final String scriptToExecute) {
        return parentPage.evaluateJavascript(scriptToExecute);
    }

    public Object evaluateJavascript(final String scriptToExecute, final Object... params) {
        return parentPage.evaluateJavascript(scriptToExecute, params);
    }

    protected String locator(final String locatorKey) {
        String locator = UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
        return isXpath(locator) ? PANEL_LOCATOR_PREFIX + locator : locator;
    }

    public WebElementFacade locate(final String locatorKey) {
        String locator = UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
        if (isXpath(locator)) {
            return findBy(By.xpath(PANEL_LOCATOR_PREFIX + locator));
        }
        return findBy(By.cssSelector(locator));
    }

    public List<WebElementFacade> locateAll(final String locatorKey) {
        String locator = UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
        if (isXpath(locator)) {
            return findAll(By.xpath(PANEL_LOCATOR_PREFIX + locator));
        }
        return findAll(By.cssSelector(locator));
    }

    protected String data(final String dataKey) {
        return UnifiedDataProviderUtil.getDataByKey(dataKey, dataProvider);
    }

    protected void waitABit(long milliseconds) {
        parentPage.waitABit(milliseconds);
    }

    @Deprecated
    protected void smartWait() {
        getTrick(Wait.class).waitForJquery(this);
    }

    public Actions withAction() {
        return parentPage.withAction();
    }

    @Deprecated
    public boolean isVisible(String locatorKey) {
        String fullLocator = dataProvider.getBase() + locator(locatorKey);
        return getTrick(Visibility.class).isElementVisible(fullLocator, panel);
    }

    @Deprecated
    public boolean isVisible(WebElementFacade element, String locatorKey) {
        String fullLocator = dataProvider.getBase() + locator(locatorKey);
        return getTrick(Visibility.class).isElementVisible(fullLocator, element);
    }

    public void sendKeys(CharSequence... charSequences) {
        panel.sendKeys(charSequences);
    }

    public WebElementFacade asWebElement() {
        return panel;
    }
}
