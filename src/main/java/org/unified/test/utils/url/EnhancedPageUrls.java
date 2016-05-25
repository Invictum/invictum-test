package org.unified.test.utils.url;

import net.serenitybdd.core.pages.PageUrls;
import net.thucydides.core.webdriver.Configuration;
import org.unified.test.Log;
import org.unified.test.pages.AbstractPage;
import org.unified.test.unified.data.provider.UnifiedDataProviderFactory;

import java.util.Map;

public class EnhancedPageUrls extends PageUrls {

    private Map<String, String> availableUrls;
    private String pageName;

    public EnhancedPageUrls(AbstractPage page, Configuration configuration) {
        super(page, configuration);
    }

    public EnhancedPageUrls(AbstractPage pageObject) {
        super(pageObject);
        availableUrls = UnifiedDataProviderFactory.getInstance(pageObject).getUrls();
        pageName = pageObject.getClass().getSimpleName();
    }

    private String urlWithParametersSubstituted(final String template, final String[] parameterValues) {
        String url = template;
        for (int i = 0; i < parameterValues.length; i++) {
            String variable = String.format("{%d}", i + 1);
            url = url.replace(variable, parameterValues[i]);
        }
        return url;
    }

    @Override
    public String getStartingUrl() {
        String fullPageUrl = UrlUtil.buildPageUrl(getSystemBaseUrl(), availableUrls);
        Log.info("Using {} url for {} page", fullPageUrl, pageName);
        return fullPageUrl;
    }

    @Override
    public String getStartingUrl(String... parameterValues) {
        String fullPageUrl = UrlUtil.buildPageUrl(getSystemBaseUrl(), availableUrls);
        fullPageUrl = urlWithParametersSubstituted(fullPageUrl, parameterValues);
        Log.info("Using {} url for {} page", fullPageUrl, pageName);
        return fullPageUrl;
    }

    @Override
    public String getNamedUrl(String name) {
        if (!availableUrls.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Url with %s key is not found for %s", name, pageName));
        }
        String namedUrl = availableUrls.get(name);
        Log.debug("Using {} url for {} key", namedUrl, name);
        return availableUrls.get(name);
    }
}
