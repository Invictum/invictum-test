package com.github.invictum.test.tricks;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.tricks.Wait;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class WaitTest {

    private Wait sud;
    private AbstractPage pageMock;

    @Before
    public void setUp() {
        sud = new Wait();
        pageMock = mock(AbstractPage.class);
        when(pageMock.evaluateJavascript("return jQuery.active")).thenReturn(Long.valueOf("0"));
        sud.setContext(pageMock);
    }

    @Test
    public void waitForJqueryTest() {
        sud.waitForJquery();
        verify(pageMock, times(1)).waitABit(0);
        verify(pageMock, times(2)).waitABit(100);
    }

    @Test
    public void waitForJqueryStaticWaitTest() {
        sud.waitForJquery(new Object(), 500);
        verify(pageMock, times(1)).waitABit(500);
        verify(pageMock, times(2)).waitABit(100);
    }

    @Test
    public void waitForJqueryCustomStepTest() {
        sud.waitForJquery(new Object(), 0, 250);
        verify(pageMock, times(1)).waitABit(0);
        verify(pageMock, times(2)).waitABit(250);
    }
}
