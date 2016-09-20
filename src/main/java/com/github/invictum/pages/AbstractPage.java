package com.github.invictum.pages;

import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.PanelFactory;
import com.github.invictum.tricks.Wait;
import com.github.invictum.tricks.core.AbstractTrick;
import com.github.invictum.tricks.core.TrickFactory;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import com.github.invictum.utils.url.EnhancedPageUrls;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class AbstractPage extends PageObject {

    private UnifiedDataProvider dataProvider;
    private EnhancedPageUrls pageUrls;

    public AbstractPage() {
        super();
        initPage();
    }

    public AbstractPage(WebDriver driver) {
        super(driver);
        initPage();
    }

    public AbstractPage(WebDriver driver, int ajaxTimeout) {
        super(driver, ajaxTimeout);
        initPage();
    }

    private void initPage() {
        pageUrls = new EnhancedPageUrls(this);
        setPageUrls(pageUrls);
        dataProvider = UnifiedDataProviderFactory.getInstance(this);
    }

    /**
     * Method checks locator relation to xpath or css.
     *
     * @param locator String
     * @return boolean result
     */
    public boolean isXpath(String locator) {
        return locator.matches("^(//|html/).+$");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    /**
     * Returns panel initialized with caller page.
     *
     * @param panelClass T
     * @param <T>        AbstractPanel
     * @return T
     */
    public <T extends AbstractPanel> T getPanel(final Class<T> panelClass) {
        return PanelFactory.get(panelClass, this);
    }

    /**
     * Returns a list of initialized panel related to caller page.
     *
     * @param panelClass
     * @param <T>
     * @return List T
     */
    public <T extends AbstractPanel> List<T> getPanels(final Class<T> panelClass) {
        return PanelFactory.getAll(panelClass, this);
    }

    /**
     * Compares current and page pattern URLs.
     *
     * @return boolean result
     */
    public boolean isPageUrlCompatible() {
        return getDriver().getCurrentUrl().matches(pageUrls.getPageUrlPattern());
    }

    /**
     * Returns Trick with initialized context.
     *
     * @param trickClass T
     * @param <T>        AbstractTrick
     * @return T
     */
    public <T extends AbstractTrick> T getTrick(Class<T> trickClass) {
        return TrickFactory.getTrick(trickClass, this);
    }

    /**
     * Returns textual representation of locator related to current page.
     *
     * @param locatorKey String
     * @return String
     */
    protected String locator(final String locatorKey) {
        return UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
    }

    /**
     * Returns textual data related to current page.
     *
     * @param dataKey String
     * @return String
     */
    protected String data(final String dataKey) {
        return UnifiedDataProviderUtil.getDataByKey(dataKey, dataProvider);
    }

    /**
     * Initializes WebElement by it locator key in context of current page.
     *
     * @param locatorKey String
     * @return WebElementFacade
     */
    public WebElementFacade locate(final String locatorKey) {
        String locator = locator(locatorKey);
        return isXpath(locator) ? find(By.xpath(locator)) : find(By.cssSelector(locator));
    }

    /**
     * Initializes WebElements by its locator keys in context of current page.
     *
     * @param locatorKey String
     * @return List WebElementFacade
     */
    public List<WebElementFacade> locateAll(final String locatorKey) {
        String locator = locator(locatorKey);
        return isXpath(locator) ? findAll(By.xpath(locator)) : findAll(By.cssSelector(locator));
    }

    @Override
    public String updateUrlWithBaseUrlIfDefined(String startingUrl) {
        return startingUrl;
    }

    /**
     * Method with changed level from protected to public
     *
     * @param timeInMilliseconds Long
     */
    public void waitABit(long timeInMilliseconds) {
        super.waitABit(timeInMilliseconds);
    }

    @Deprecated
    @WhenPageOpens
    public AbstractPage smartWait() {
        getTrick(Wait.class).waitForJquery(this);
        return this;
    }

    /**
     * Opens current page at custom url.
     *
     * @param url String
     */
    public void openCustom(String url) {
        pageUrls.overrideUrlOnce(url);
        super.openUnchecked();
    }

    /**
     * Opens page at custom url with parameters.
     *
     * @param url    String
     * @param params String[]
     */
    public void openCustom(String url, String... params) {
        pageUrls.overrideUrlOnce(url);
        super.openUnchecked(params);
    }
}
