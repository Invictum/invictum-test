package org.unified.test.utils.url;

import net.serenitybdd.core.Serenity;
import org.unified.test.Log;
import org.unified.test.utils.properties.PropertiesUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UrlUtil {

    public static final String URL_CONFIG_TAG = "url";
    public static final String DEFAULT_URL_KEY = "default";
    public static final String DELIMER = ":";
    public static final String URL_PROPERTY_BASE = "webdriver.base.url";

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
            Log.error("Failed to analyse default page URL: Starting URL: {}, Base URL: {}", starting, base);
            Log.error("URL analysis failed with exception:", e);
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

    private static String processSuffix(final Map<String, String> avaliableUrls, final String urlKey) {
        if (avaliableUrls.containsKey(urlKey)) {
            return avaliableUrls.get(urlKey);
        } else if (!avaliableUrls.containsKey(DEFAULT_URL_KEY)) {
            throw new IllegalArgumentException("Url with specified key is not defined.");
        }
        return avaliableUrls.get(DEFAULT_URL_KEY);
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
}
