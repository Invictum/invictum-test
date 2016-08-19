package com.github.invictum.utils.url;

import com.github.invictum.utils.properties.PropertiesUtil;
import net.serenitybdd.core.Serenity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UrlUtil {

    public static final String URL_CONFIG_TAG = "url";
    public static final String DEFAULT_URL_KEY = "default";
    public static final String DELIMER = ":";
    public static final String URL_PROPERTY_BASE = "webdriver.base.url";
    private final static Logger LOG = LoggerFactory.getLogger(UrlUtil.class);

    public static String replaceHost(final String starting, final String base) {
        String result = null;
        try {
            URL startingUrl = new URL(starting);
            URL baseUrl = new URL(base);
            String startingHostComponent = hostComponentFrom(startingUrl.getProtocol(), startingUrl.getHost(),
                    startingUrl.getPort());
            String baseHostComponent = hostComponentFrom(baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort());
            result = starting.replaceFirst(startingHostComponent, baseHostComponent);
        } catch (MalformedURLException e) {
            LOG.error("Failed to analyse default page URL: Starting URL: {}, Base URL: {}", starting, base);
            LOG.error("URL analysis failed with exception:", e);
        }
        return result;
    }

    private static String hostComponentFrom(final String protocol, final String host, final int port) {
        StringBuilder hostComponent = new StringBuilder(protocol);
        hostComponent.append("://");
        hostComponent.append(host);
        if (port > 0) {
            hostComponent.append(":");
            hostComponent.append(port);
        }
        return hostComponent.toString();
    }

    private static String processBase(final String baseUrl, final String baseUrlKey) {
        return replaceHost(baseUrl, getPageUrlByKey(baseUrlKey));
    }

    private static String processSuffix(final Map<String, String> availableUrls, final String urlKey) {
        if (availableUrls.containsKey(urlKey)) {
            LOG.debug("Found url for {} key, use {} suffix", urlKey, availableUrls.get(urlKey));
            return availableUrls.get(urlKey);
        } else if (!availableUrls.containsKey(DEFAULT_URL_KEY)) {
            LOG.debug("Default url key isn't found, use base URL");
            return StringUtils.EMPTY;
        }
        LOG.debug("Found default url key, use {} suffix", availableUrls.get(DEFAULT_URL_KEY));
        return availableUrls.get(DEFAULT_URL_KEY);
    }

    private static String getPageUrlByKey(String urlKey) {
        String baseUrl = PropertiesUtil.getProperty(String.format("%s.%s", URL_PROPERTY_BASE, urlKey));
        if (baseUrl.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s url key is not found.", urlKey));
        }
        return baseUrl;
    }

    public static String buildPageUrl(final String baseUrl, final Map<String, String> avaliableUrls) {
        if (Serenity.getCurrentSession().getMetaData().containsKey(URL_CONFIG_TAG)) {
            String[] urlConfigTag = Serenity.getCurrentSession().getMetaData().get(URL_CONFIG_TAG).split(DELIMER);
            if (urlConfigTag.length > 1) {
                return processBase(baseUrl, urlConfigTag[0]) + processSuffix(avaliableUrls, urlConfigTag[1]);
            } else {
                return baseUrl + processSuffix(avaliableUrls, urlConfigTag[0]);
            }
        }
        return baseUrl + processSuffix(avaliableUrls, DEFAULT_URL_KEY);
    }

    public static String buildPageUrlUnsafe(final String key, final Map<String, String> availableUrls) {
        return getPageUrlByKey(key) + processSuffix(availableUrls, DEFAULT_URL_KEY);
    }
}
