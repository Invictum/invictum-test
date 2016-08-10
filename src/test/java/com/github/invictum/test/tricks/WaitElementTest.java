package com.github.invictum.test.tricks;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.Visibility;
import com.github.invictum.tricks.WaitElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class WaitElementTest {

    private WaitElement sud;
    private AbstractPage pageMock;
    private Visibility visibilityMock;

    @Before
    public void setUp() {
        sud = new WaitElement();
        pageMock = mock(AbstractPage.class);
        visibilityMock = mock(Visibility.class);
        when(pageMock.getTrick(Visibility.class)).thenReturn(visibilityMock);
        sud.setContext(pageMock);
    }

    @Test
    public void isVisibleTest() {
        when(visibilityMock.isElementVisible("locator")).thenReturn(false, true);
        sud.isVisible("locator");
        verify(pageMock, times(1)).waitABit(200);
    }

    @Test
    public void isVisibleCustomStepTest() {
        when(visibilityMock.isElementVisible("locator")).thenReturn(false, true);
        sud.isVisible("locator", 2000, 50);
        verify(pageMock, times(1)).waitABit(50);
    }

    @Test(expected = IllegalStateException.class)
    public void isVisibleCustomTimeoutTest() {
        when(visibilityMock.isElementVisible("locator")).thenReturn(false);
        sud.isVisible("locator", 300);
    }

    @Test
    public void isInvisibleTest() {
        when(visibilityMock.isElementVisible("locator")).thenReturn(true, false);
        sud.isInvisible("locator");
        verify(pageMock, times(1)).waitABit(200);
    }

    @Test
    public void isInvisibleCustomStepTest() {
        when(visibilityMock.isElementVisible("locator")).thenReturn(true, false);
        sud.isInvisible("locator", 2000, 50);
        verify(pageMock, times(1)).waitABit(50);
    }

    @Test(expected = IllegalStateException.class)
    public void isInvisibleCustomTimeoutTest() {
        when(visibilityMock.isElementVisible("locator")).thenReturn(true);
        sud.isInvisible("locator", 300);
    }
}
