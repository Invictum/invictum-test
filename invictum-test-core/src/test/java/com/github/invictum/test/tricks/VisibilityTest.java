package com.github.invictum.test.tricks;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.Visibility;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class VisibilityTest {

    private Visibility sud;
    private AbstractPage pageMock;
    private WebElementFacadeImpl webElementMock;
    private By xpathLocator = By.xpath("//locator");
    private By cssLocator = By.xpath("div.class");

    @Before
    public void setUp() {
        sud = new Visibility();
        pageMock = mock(AbstractPage.class);
        webElementMock = mock(WebElementFacadeImpl.class);
        sud.setContext(pageMock);
    }

    @Test
    public void elementVisibleTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        when(pageMock.findAll(xpathLocator)).thenReturn(elementsMock);
        assertThat("Element isn't visible.", sud.isElementVisible(xpathLocator), equalTo(true));
    }

    @Test
    public void elementInvisibleTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(false);
        when(pageMock.findAll(cssLocator)).thenReturn(elementsMock);
        assertThat("Element is visible.", sud.isElementVisible(cssLocator), equalTo(false));
    }

    @Test
    public void elementVisibleInPanelTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        WebElementFacade panelElement = mock(WebElementFacadeImpl.class);
        when(panelElement.thenFindAll(xpathLocator)).thenReturn(elementsMock);
        assertThat("Element is invisible in panel.", sud.isElementVisible(xpathLocator, panelElement), equalTo(true));
    }

    @Test
    public void defaultTimeoutValuePickTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        when(pageMock.findAll(xpathLocator)).thenReturn(elementsMock);
        sud.isElementVisible(xpathLocator);
        verify(pageMock, times(1)).setImplicitTimeout(500, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void customTimeoutValuePickTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        when(pageMock.findAll(cssLocator)).thenReturn(elementsMock);
        sud.isElementVisible(cssLocator, null, 1000);
        verify(pageMock, times(1)).setImplicitTimeout(1000, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }
}
