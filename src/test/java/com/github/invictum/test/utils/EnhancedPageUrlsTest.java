package com.github.invictum.test.utils;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.utils.url.EnhancedPageUrls;
import com.github.invictum.utils.url.UrlUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnifiedDataProviderFactory.class, UrlUtil.class})
public class EnhancedPageUrlsTest {

    private AbstractPage pageMock = null;

    @Before
    public void setupTest() throws Exception {
        pageMock = mock(AbstractPage.class);
        mockStatic(UnifiedDataProviderFactory.class);
        UnifiedDataProvider dataProvider = new UnifiedDataProvider();
        when(UnifiedDataProviderFactory.class, "getInstance", anyObject()).thenReturn(dataProvider);
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
}
