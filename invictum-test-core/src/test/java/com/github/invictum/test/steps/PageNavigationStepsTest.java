package com.github.invictum.test.steps;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.steps.PageNavigationSteps;
import com.github.invictum.test.steps.instances.TestPage;
import net.thucydides.core.pages.Pages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractPage.class)
public class PageNavigationStepsTest {

    @Mock
    private Pages pagesMock;

    private TestPage pageMock = null;

    private PageNavigationSteps sut = null;

    @Before
    public void beforeTest() throws Exception {
        pageMock = mock(TestPage.class);
        when(pagesMock.getPage(TestPage.class)).thenReturn(pageMock);
        sut = new PageNavigationSteps(pagesMock);
    }

    @Test
    public void openByClassTest() {
        doNothing().when(pageMock).open(new String[] {});
        sut.openPage(TestPage.class);
        verify(pageMock, times(1)).setImplicitTimeout(anyInt(), any(TimeUnit.class));
        verify(pageMock, times(1)).open(new String[] {});
        verify(pageMock, times(1)).resetImplicitTimeout();
    }


    @Test
    public void openByClassWithParamTest() {
        doNothing().when(pageMock).open(new String[] {"param1", "param2"});
        sut.openPageWithParams(TestPage.class, "param1", "param2");
        verify(pageMock, times(1)).setImplicitTimeout(anyInt(), any(TimeUnit.class));
        verify(pageMock, times(1)).open(new String[] {"param1", "param2"});
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void openByNameTest() {
        doNothing().when(pageMock).open(new String[] {});
        sut.openPage("Test");
        verify(pageMock, times(1)).setImplicitTimeout(anyInt(), any(TimeUnit.class));
        verify(pageMock, times(1)).open(new String[] {});
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void openByNameWithParamsTest() {
        doNothing().when(pageMock).open(new String[] {"param"});
        sut.openPageWithParams("Test", "param");
        verify(pageMock, times(1)).setImplicitTimeout(anyInt(), any(TimeUnit.class));
        verify(pageMock, times(1)).open(new String[] {"param"});
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test(expected = IllegalStateException.class)
    public void nonExistingPageTest() {
        sut.openPage("InvalidPageClassName");
    }
}
