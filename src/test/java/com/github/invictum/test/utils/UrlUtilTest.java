package com.github.invictum.test.utils;

import com.github.invictum.utils.properties.PropertiesUtil;
import com.github.invictum.utils.url.UrlUtil;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.sessions.TestSessionVariables;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertiesUtil.class, Serenity.class})
public class UrlUtilTest {

    private Map<String, String> urls = new HashMap<>();
    private TestSessionVariables<String, String> sessionMap = new TestSessionVariables<>();

    @Before
    public void setupTest() throws Exception {
        mockStatic(PropertiesUtil.class);
        mockStatic(Serenity.class);
        sessionMap.clear();
        urls.clear();
    }

    @Test
    public void buildPageUrlUnsafeTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn("http://example.org");
        urls.put("default", "/url");
        assertThat("Url was build wrong.", UrlUtil.buildPageUrlUnsafe("default", urls),
                equalTo("http://example.org/url"));
    }

    @Test
    public void buildPageUrlUnsafeNoDefaultTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn("http://example.org");
        assertThat("Url was build wrong.", UrlUtil.buildPageUrlUnsafe("key", urls), equalTo("http://example.org"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyBaseUrlTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn(StringUtils.EMPTY);
        UrlUtil.buildPageUrlUnsafe("key", urls);
    }

    @Test
    public void buildPageUrlNoConfigTagTest() throws Exception {
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        urls.put("default", "/key");
        assertThat("Url was build wrong.", UrlUtil.buildPageUrl("http://example.org", urls),
                equalTo("http://example.org/key"));
    }

    @Test
    public void buildPageUrlNoConfigTagDefaultKeyTest() throws Exception {
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        assertThat("Url was build wrong.", UrlUtil.buildPageUrl("http://example.org", urls),
                equalTo("http://example.org"));
    }

    @Test
    public void buildPageUrlCustomSuffixTest() throws Exception {
        sessionMap.addMetaData("url", "key");
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        urls.put("key", "/key");
        assertThat("Url was build wrong.", UrlUtil.buildPageUrl("http://example.org", urls),
                equalTo("http://example.org/key"));
    }

    @Test
    public void buildPageUrlFullCustomTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn("https://example.com");
        sessionMap.addMetaData("url", "base:key");
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        urls.put("key", "/key");
        assertThat("Url was build wrong.", UrlUtil.buildPageUrl("http://example.org", urls),
                equalTo("https://example.com/key"));
    }

    @Test
    public void buildPageUrlFullCustomNoDefaultTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn("https://example.com");
        sessionMap.addMetaData("url", "base:default");
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        assertThat("Url was build wrong.", UrlUtil.buildPageUrl("http://example.org", urls),
                equalTo("https://example.com"));
    }

    @Test
    public void buildPageUrlFullCustomDefaultKeyTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn("http://example.org");
        sessionMap.addMetaData("url", "base:someKey");
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        urls.put("default", "/key");
        assertThat("Url was build wrong.", UrlUtil.buildPageUrl("http://example.org", urls),
                equalTo("http://example.org/key"));
    }

    @Test
    public void buildPageUrlFullCustomPortTest() throws Exception {
        when(PropertiesUtil.class, "getProperty", anyString()).thenReturn("http://host:8080");
        sessionMap.addMetaData("url", "base:default");
        when(Serenity.class, "getCurrentSession").thenReturn(sessionMap);
        UrlUtil.buildPageUrl("http://host:8080", urls);
    }
}
