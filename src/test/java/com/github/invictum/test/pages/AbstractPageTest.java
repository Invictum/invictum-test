package com.github.invictum.test.pages;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import com.github.invictum.utils.url.EnhancedPageUrls;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractPage.class, UnifiedDataProviderFactory.class, UnifiedDataProviderUtil.class})
public class AbstractPageTest {

    private static EnhancedPageUrls urlsMock = null;
    private WebDriver driver = null;

    @Before
    public void setupTest() throws Exception {
        urlsMock = mock(EnhancedPageUrls.class);
        whenNew(EnhancedPageUrls.class).withAnyArguments().thenReturn(urlsMock);
        mockStatic(UnifiedDataProviderFactory.class);
        mockStatic(UnifiedDataProviderUtil.class);
        UnifiedDataProvider dataProvider = new UnifiedDataProvider();
        when(UnifiedDataProviderFactory.class, "getInstance", anyObject()).thenReturn(dataProvider);
        driver = mock(WebDriver.class);
    }

    @Test
    public void toStringTest() {
        AbstractPage page = new AbstractPage();
        assertThat("To string method value is wrong.", page.toString(), equalTo("AbstractPage"));
    }

    @Test
    public void isPageUrlCompatibleTest() throws Exception {
        when(driver.getCurrentUrl()).thenReturn("http://localhost/path/session#part");
        when(urlsMock, "getPageUrlPattern").thenReturn("http://localhost/path/.+?");
        AbstractPage page = new AbstractPage(driver);
        assertThat("Page isn't compatible to current url.", page.isPageUrlCompatible(), equalTo(true));
    }

    @Test
    public void isPageUrlWithParamsCompatibleTest() throws Exception {
        when(driver.getCurrentUrl()).thenReturn("http://localhost/path/param1/path/param2/");
        when(urlsMock, "getPageUrlPattern").thenReturn("http://localhost/path/.+?/path/.+?/");
        AbstractPage page = new AbstractPage(driver);
        assertThat("Page isn't compatible to current url.", page.isPageUrlCompatible(), equalTo(true));
    }

    @Test
    public void isXpathTest() {
        AbstractPage page = new AbstractPage();
        assertThat("Specified locator classified wrong.", page.isXpath("//div[@id]"), equalTo(true));
    }

    @Test
    public void isXpathFullDescribedTest() {
        AbstractPage page = new AbstractPage();
        assertThat("Specified locator classified wrong.", page.isXpath("html/div[@id]"), equalTo(true));
    }

    @Test
    public void isXpathCssTest() {
        AbstractPage page = new AbstractPage();
        assertThat("Specified locator classified wrong.", page.isXpath("div.class"), equalTo(false));
    }

    @Test
    public void locateByXpathTest() throws Exception {
        String locatorValue = "//xpath";
        when(UnifiedDataProviderUtil.class, "getLocatorByKey", anyObject(), anyObject()).thenReturn(locatorValue);
        when(driver.findElement(By.xpath(locatorValue))).thenReturn(null);
        new AbstractPage(driver).locate("locatorKey");
        verify(driver, times(1)).findElement(By.xpath(locatorValue));
    }

    @Test
    public void locateAllByXpathTest() throws Exception {
        String locatorValue = "//xpath";
        when(UnifiedDataProviderUtil.class, "getLocatorByKey", anyObject(), anyObject()).thenReturn(locatorValue);
        when(driver.findElements(By.xpath(locatorValue))).thenReturn(new ArrayList<WebElement>());
        new AbstractPage(driver).locateAll("locatorKey");
        verify(driver, times(1)).findElements(By.xpath(locatorValue));
    }

    @Test
    public void locateByCssTest() throws Exception {
        String locatorValue = ".css";
        when(UnifiedDataProviderUtil.class, "getLocatorByKey", anyObject(), anyObject()).thenReturn(locatorValue);
        when(driver.findElement(By.cssSelector(locatorValue))).thenReturn(null);
        new AbstractPage(driver).locate("locatorKey");
        verify(driver, times(1)).findElement(By.cssSelector(locatorValue));
    }

    @Test
    public void locateAllByCssTest() throws Exception {
        String locatorValue = ".css";
        when(UnifiedDataProviderUtil.class, "getLocatorByKey", anyObject(), anyObject()).thenReturn(locatorValue);
        when(driver.findElements(By.cssSelector(locatorValue))).thenReturn(new ArrayList<WebElement>());
        new AbstractPage(driver).locateAll("locatorKey");
        verify(driver, times(1)).findElements(By.cssSelector(locatorValue));
    }
}
