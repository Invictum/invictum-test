package com.github.invictum.test.utils;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.utils.url.EnhancedPageUrls;
import com.github.invictum.utils.url.UrlUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnifiedDataProviderFactory.class, UrlUtil.class})
@Ignore
public class EnhancedPageUrlsTest {

    private AbstractPage pageMock = null;
    private UnifiedDataProvider dataProvider = null;

    @Before
    public void setupTest() throws Exception {
        pageMock = mock(AbstractPage.class);
        mockStatic(UnifiedDataProviderFactory.class);
        dataProvider = new UnifiedDataProvider();
        when(UnifiedDataProviderFactory.class, "getInstance", Matchers.anyObject()).thenReturn(dataProvider);
        mockStatic(UrlUtil.class);
    }

    @Test
    public void getPageUrlPatternTest() throws Exception {
        when(UrlUtil.class, "buildPageUrl", anyObject(), anyObject()).thenReturn("http://host/path");
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        assertThat("Url was build wrong.", sud.getPageUrlPattern(), equalTo("http://host/path(.+?|)"));
    }

    @Test
    public void getPageUrlPatternNoEndingTest() throws Exception {
        when(UrlUtil.class, "buildPageUrl", anyObject(), anyObject()).thenReturn("http://host/path.+?");
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        assertThat("Url was build wrong..", sud.getPageUrlPattern(), equalTo("http://host/path.+?"));
    }

    @Test
    public void getPageUrlPatternWithParamsTest() throws Exception {
        when(UrlUtil.class, "buildPageUrl", anyObject(), anyObject()).thenReturn("http://host:8080/{1}/path/");
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        assertThat("Url was build wrong.", sud.getPageUrlPattern(), equalTo("http://host:8080/.+?/path/(.+?|)"));
    }

    @Test
    public void getPageUrlPatternWithParamsNoEndingTest() throws Exception {
        when(UrlUtil.class, "buildPageUrl", anyObject(), anyObject()).thenReturn("http://host:8080/path-{1}/{2}");
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        assertThat("Url was build wrong.", sud.getPageUrlPattern(), equalTo("http://host:8080/path-.+?/.+?"));
    }

    @Test
    public void getNamedUrlTest() throws Exception {
        Map<String, String> urls = new HashMap<>();
        urls.put("key", "/url");
        dataProvider.setUrls(urls);
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        assertThat("Url key is absent.", sud.getNamedUrl("key"), equalTo("/url"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNamedUrlNoKeyTest() throws Exception {
        dataProvider.setUrls(new HashMap<String, String>());
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        sud.getNamedUrl("key");
    }

    @Test
    public void getStartingUrlTest() throws Exception {
        String url = "http://example.org/";
        when(UrlUtil.class, "buildPageUrlUnsafe", anyObject(), anyObject()).thenReturn(url);
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        sud.overrideUrlOnce("key");
        assertThat("Starting Url is wrong.", sud.getStartingUrl(), equalTo(url));
    }

    @Test
    public void overrideOnceTest() throws Exception {
        when(UrlUtil.class, "buildPageUrlUnsafe", anyObject(), anyObject()).thenReturn("/someUrl");
        EnhancedPageUrls sud = new EnhancedPageUrls(pageMock);
        sud.overrideUrlOnce("key");
        sud.getStartingUrl();
        String actual = sud.getStartingUrl();
        assertThat("Url key isn't null.", actual, nullValue());
    }
}
