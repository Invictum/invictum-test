package com.github.invictum.pages;

import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.panels.PanelFactory;
import com.github.invictum.tricks.Visibility;
import com.github.invictum.tricks.core.AbstractTrick;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import com.github.invictum.utils.url.EnhancedPageUrls;
import com.github.invictum.tricks.Wait;
import com.github.invictum.tricks.core.TrickFactory;

import java.util.List;

public class AbstractPage extends PageObject {

    private UnifiedDataProvider dataProvider;

    public AbstractPage() {
        super();
        setPageUrls(new EnhancedPageUrls(this));
        dataProvider = UnifiedDataProviderFactory.getInstance(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public <T extends AbstractPanel> T getPanel(final Class<T> panelClass) {
        return PanelFactory.get(panelClass, this);
    }

    public <T extends AbstractTrick> T getTrick(Class<T> trickClass) {
        return TrickFactory.getTrick(trickClass, this);
    }

    protected String locator(final String locatorKey) {
        return "." + UnifiedDataProviderUtil.getLocatorByKey(locatorKey, dataProvider);
    }

    protected String data(final String dataKey) {
        return UnifiedDataProviderUtil.getDataByKey(dataKey, dataProvider);
    }

    public WebElementFacade locate(final String locatorKey) {
        return findBy(locator(locatorKey));
    }

    public List<WebElementFacade> locateAll(final String locatorKey) {
        return findAll(locator(locatorKey));
    }

    @Override
    public String updateUrlWithBaseUrlIfDefined(String startingUrl) {
        return startingUrl;
    }

    public void waitABit(long timeInMilliseconds) {
        super.waitABit(timeInMilliseconds);
    }

    @WhenPageOpens
    public AbstractPage smartWait() {
        getTrick(Wait.class).waitForJquery(this);
        return this;
    }

    public boolean isVisible(String locatorKey) {
        return getTrick(Visibility.class).isElementVisible(locator(locatorKey), null);
    }

    public boolean isVisible(WebElementFacade element, String locatorKey) {
        return getTrick(Visibility.class).isElementVisible(locator(locatorKey), element);
    }
}
