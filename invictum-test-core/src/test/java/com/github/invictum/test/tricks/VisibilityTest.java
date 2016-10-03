package com.github.invictum.test.tricks;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.Visibility;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
        when(pageMock.findAll("//locator")).thenReturn(elementsMock);
        assertThat("Element isn't visible.", sud.isElementVisible("//locator"), equalTo(true));
    }

    @Test
    public void elementInvisibleTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(false);
        when(pageMock.findAll("//locator")).thenReturn(elementsMock);
        assertThat("Element is visible.", sud.isElementVisible("//locator"), equalTo(false));
    }

    @Test
    public void elementVisibleInPanelTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        WebElementFacade panelElement = mock(WebElementFacadeImpl.class);
        when(panelElement.thenFindAll("//locator")).thenReturn(elementsMock);
        assertThat("Element is invisible in panel.", sud.isElementVisible("//locator", panelElement), equalTo(true));
    }

    @Test
    public void defaultTimeoutValuePickTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        when(pageMock.findAll("//locator")).thenReturn(elementsMock);
        sud.isElementVisible("//locator");
        verify(pageMock, times(1)).setImplicitTimeout(500, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void customTimeoutValuePickTest() {
        List<WebElementFacade> elementsMock = new ArrayList<>();
        elementsMock.add(webElementMock);
        when(webElementMock.isVisible()).thenReturn(true);
        when(pageMock.findAll("//locator")).thenReturn(elementsMock);
        sud.isElementVisible("//locator", null, 1000);
        verify(pageMock, times(1)).setImplicitTimeout(1000, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }
}
