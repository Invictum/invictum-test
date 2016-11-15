package com.github.invictum.test.tricks;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.SafePicker;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SafePickerTest {

    private SafePicker sud;
    private AbstractPage pageMock;
    private WebElementFacadeImpl webElementMock;
    private By xpathLocator = By.xpath("//locator");
    private By cssLocator = By.cssSelector(".class");

    @Before
    public void setUp() {
        sud = new SafePicker();
        pageMock = mock(AbstractPage.class);
        webElementMock = mock(WebElementFacadeImpl.class);
        sud.setContext(pageMock);
    }

    @Test
    public void pickTest() {
        when(webElementMock.getText()).thenReturn("value");
        when(pageMock.find(xpathLocator)).thenReturn(webElementMock);
        assertThat("Pick result is wrong.", sud.pick(xpathLocator), equalTo("value"));
    }

    @Test
    public void pickOnElementTest() {
        WebElementFacadeImpl parentWebElementMock = mock(WebElementFacadeImpl.class);
        when(parentWebElementMock.find(xpathLocator)).thenReturn(webElementMock);
        when(webElementMock.getText()).thenReturn("test");
        when(pageMock.find(xpathLocator)).thenReturn(webElementMock);
        assertThat("Pick result is wrong.", sud.onElement(parentWebElementMock).pick(xpathLocator), equalTo("test"));
    }

    @Test
    public void pickOnPageTest() {
        when(webElementMock.getText()).thenReturn("test");
        when(pageMock.find(xpathLocator)).thenReturn(webElementMock);
        assertThat("Pick result is wrong.", sud.onElement(webElementMock).onPage().pick(xpathLocator), equalTo("test"));
    }

    @Test
    public void nullDefaultValuePickTest() {
        when(pageMock.find(cssLocator)).thenThrow(new RuntimeException());
        assertThat("Pick result isn't null.", sud.pick(cssLocator), equalTo(null));
    }

    @Test
    public void customDefaultValuePickTest() {
        when(pageMock.find(cssLocator)).thenThrow(new RuntimeException());
        assertThat("Custom pick result is wrong.", sud.withDefaultValue("default").pick(cssLocator),
                equalTo("default"));
    }

    @Test
    public void defaultTimeoutValuePickTest() {
        when(pageMock.find(xpathLocator)).thenThrow(new RuntimeException());
        sud.pick(xpathLocator);
        verify(pageMock, times(1)).setImplicitTimeout(100, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void customTimeoutValuePickTest() {
        when(pageMock.find(cssLocator)).thenThrow(new RuntimeException());
        sud.withTimeout(200).pick(cssLocator);
        verify(pageMock, times(1)).setImplicitTimeout(200, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }
}
