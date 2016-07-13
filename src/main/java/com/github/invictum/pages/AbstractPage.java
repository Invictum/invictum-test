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
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;

import java.util.List;

public class AbstractPage extends PageObject {

    private UnifiedDataProvider dataProvider;
    private EnhancedPageUrls pageUrls;

    public AbstractPage() {
        super();
        pageUrls = new EnhancedPageUrls(this);
        setPageUrls(pageUrls);
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

    public void openCustom(String url) {
        pageUrls.overrideUrlOnce(url);
        super.openUnchecked();
    }

    public void openCustom(String url, String... params) {
        pageUrls.overrideUrlOnce(url);
        super.openUnchecked(params);
    }
}
