package com.github.invictum.test.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.PanelFactory;
import com.github.invictum.panels.strategy.NoWaitStrategy;
import com.github.invictum.test.panels.instances.PanelWithDisabledStrategy;
import com.github.invictum.test.panels.instances.TestFloatingPanel;
import com.github.invictum.test.panels.instances.TestLocalStrategyPanel;
import com.github.invictum.test.panels.instances.TestPanel;
import com.github.invictum.tricks.Visibility;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import net.serenitybdd.core.annotations.findby.By;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnifiedDataProviderFactory.class})
public class PanelFactoryTest {

    private AbstractPage pageMock = null;
    UnifiedDataProvider dataProvider = null;

    @Before
    public void setupTest() throws Exception {
        mockStatic(UnifiedDataProviderFactory.class);
        dataProvider = new UnifiedDataProvider();
        dataProvider.setBase("//div");
        when(UnifiedDataProviderFactory.class, "getInstance", anyObject()).thenReturn(dataProvider);
        pageMock = mock(AbstractPage.class);
        when(pageMock.isXpath("//div")).thenReturn(true);
        when(pageMock.getDriver()).thenReturn(null);
    }

    @Test
    public void getViaXpathTest() {
        PanelFactory.get(TestPanel.class, pageMock);
        verify(pageMock, times(1)).find(By.xpath("//div"));
    }

    @Test
    public void getAllViaXpathTest() {
        PanelFactory.getAll(TestPanel.class, pageMock);
        verify(pageMock, times(1)).findAll(By.xpath("//div"));
    }

    @Test
    public void getViaCssTest() {
        when(pageMock.isXpath("//div")).thenReturn(false);
        PanelFactory.get(TestPanel.class, pageMock);
        verify(pageMock, times(1)).find(By.cssSelector("//div"));
    }

    @Test
    public void getAllViaCssTest() {
        when(pageMock.isXpath("//div")).thenReturn(false);
        PanelFactory.getAll(TestPanel.class, pageMock);
        verify(pageMock, times(1)).findAll(By.cssSelector("//div"));
    }

    @Test
    public void localStrategyTest() {
        PanelFactory.get(TestLocalStrategyPanel.class, pageMock);
        verify(pageMock, times(1)).resetImplicitTimeout();
    }

    @Test
    public void globalStrategyTest() {
        NoWaitStrategy strategyMock = mock(NoWaitStrategy.class);
        PanelFactory.setPanelInitWaitStrategy(strategyMock);
        PanelFactory.get(TestPanel.class, pageMock);
        verify(strategyMock, times(1)).apply(pageMock);
    }

    @Test
    public void disabledGlobalStrategyTest() {
        NoWaitStrategy strategyMock = mock(NoWaitStrategy.class);
        PanelFactory.setPanelInitWaitStrategy(strategyMock);
        PanelFactory.get(PanelWithDisabledStrategy.class, pageMock);
        Mockito.verifyZeroInteractions(strategyMock);
    }

    @Test(expected = IllegalStateException.class)
    public void getNoBaseTest() {
        dataProvider.setBase(null);
        PanelFactory.get(TestPanel.class, pageMock);
    }

    @Test(expected = IllegalStateException.class)
    public void getAllNoBaseTest() {
        dataProvider.setBase(null);
        PanelFactory.getAll(TestPanel.class, pageMock);
    }

    @Test(expected = IllegalStateException.class)
    public void getAllFloatingNoBaseTest() {
        dataProvider.setBase(null);
        PanelFactory.getAll(TestFloatingPanel.class, pageMock);
    }

    @Test
    public void getFloatingNoBaseTest() {
        dataProvider.setBase(null);
        when(pageMock.isXpath(anyString())).thenCallRealMethod();
        PanelFactory.get(TestFloatingPanel.class, pageMock);
        verify(pageMock, times(1)).find(By.xpath(PanelFactory.FLOATING_PANEL_BASE_LOCATOR));
    }

    @Test
    public void isPanelVisibleTest() {
        String locator = "//div";
        dataProvider.setBase(locator);
        Visibility trickMock = mock(Visibility.class);
        when(pageMock.getTrick(Visibility.class)).thenReturn(trickMock);
        when(trickMock.isElementVisible(locator)).thenReturn(true);
        assertThat("Panel is invisible.", PanelFactory.isPanelVisible(TestPanel.class, pageMock), equalTo(true));
    }
}
