package com.github.invictum.test.tricks;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.SafePicker;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SafePickerTest {

    private SafePicker sud;
    private AbstractPage pageMock;
    private WebElementFacadeImpl webElementMock;

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
        when(pageMock.findBy("//locator")).thenReturn(webElementMock);
        assertThat("Pick result is wrong.", sud.pick("//locator"), equalTo("value"));
    }

    @Test
    public void nullDefaultValuePickTest() {
        when(pageMock.findBy("//locator")).thenThrow(new RuntimeException());
        assertThat("Pick result isn't null.", sud.pick("//locator"), equalTo(null));
    }

    @Test
    public void customDefaultValuePickTest() {
        when(pageMock.findBy("//locator")).thenThrow(new RuntimeException());
        assertThat("Custom pick result is wrong.", sud.pick("//locator", "default"), equalTo("default"));
    }

    @Test
    public void defaultTimeoutValuePickTest() {
        when(pageMock.findBy("//locator")).thenThrow(new RuntimeException());
        sud.pick("//locator", "default");
        verify(pageMock, times(1)).setImplicitTimeout(100, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void customTimeoutValuePickTest() {
        when(pageMock.findBy("//locator")).thenThrow(new RuntimeException());
        sud.pick("//locator", "default", 200);
        verify(pageMock, times(1)).setImplicitTimeout(200, TimeUnit.MILLISECONDS);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }
}
