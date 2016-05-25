package org.example.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import org.example.utils.url.EnhancedPageUrls;
import org.example.panels.AbstractPanel;
import org.example.panels.PanelFactory;
import org.example.tricks.Visibility;
import org.example.tricks.Wait;
import org.example.tricks.core.AbstractTrick;
import org.example.tricks.core.TrickFactory;
import org.example.unified.data.provider.UnifiedDataProvider;
import org.example.unified.data.provider.UnifiedDataProviderFactory;
import org.example.unified.data.provider.UnifiedDataProviderUtil;

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
