package com.github.invictum.utils.url;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import net.serenitybdd.core.pages.PageUrls;
import net.thucydides.core.webdriver.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EnhancedPageUrls extends PageUrls {

    private final static Logger LOG = LoggerFactory.getLogger(EnhancedPageUrls.class);
    private Map<String, String> availableUrls;
    private String pageName;
    private String overridenUrlKey;

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
        if (overridenUrlKey != null) {
            String key = overridenUrlKey;
            overridenUrlKey = null;
            return UrlUtil.buildPageUrlUnsafe(key, availableUrls);
        }
        String fullPageUrl = UrlUtil.buildPageUrl(getSystemBaseUrl(), availableUrls);
        LOG.debug("Using {} url for {} page", fullPageUrl, pageName);
        return fullPageUrl;
    }

    @Override
    public String getStartingUrl(String... parameterValues) {
        if (overridenUrlKey != null) {
            String key = overridenUrlKey;
            overridenUrlKey = null;
            return UrlUtil.buildPageUrlUnsafe(key, availableUrls);
        }
        String fullPageUrl = UrlUtil.buildPageUrl(getSystemBaseUrl(), availableUrls);
        fullPageUrl = urlWithParametersSubstituted(fullPageUrl, parameterValues);
        LOG.debug("Using {} url for {} page", fullPageUrl, pageName);
        return fullPageUrl;
    }

    @Override
    public String getNamedUrl(String name) {
        if (!availableUrls.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Url with %s key is not found for %s", name, pageName));
        }
        String namedUrl = availableUrls.get(name);
        LOG.debug("Using {} url for {} key", namedUrl, name);
        return availableUrls.get(name);
    }

    public void overrideUrlOnce(String urlKey) {
        overridenUrlKey = urlKey;
    }
}
