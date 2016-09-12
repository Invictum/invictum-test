package com.github.invictum.test.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.PanelFactory;
import com.github.invictum.panels.strategy.NoWaitStrategy;
import com.github.invictum.test.panels.instances.PanelWithDisabledStrategy;
import com.github.invictum.test.panels.instances.TestPanel;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnifiedDataProviderFactory.class})
public class PanelFactoryTest {

    private AbstractPage pageMock = null;

    @Before
    public void setupTest() throws Exception {
        mockStatic(UnifiedDataProviderFactory.class);
        UnifiedDataProvider dataProvider = new UnifiedDataProvider();
        dataProvider.setBase("//div");
        when(UnifiedDataProviderFactory.class, "getInstance", anyObject()).thenReturn(dataProvider);
        pageMock = mock(AbstractPage.class);
        when(pageMock.find(By.xpath("//div"))).thenReturn(new WebElementFacadeImpl(null, null, 0));
        when(pageMock.isXpath("//div")).thenReturn(true);
        when(pageMock.getDriver()).thenReturn(null);
    }

    @Test
    public void getTest() {
        PanelFactory.get(TestPanel.class, pageMock);
        assertThat("Initialized panel has a wrong class.", PanelFactory.get(TestPanel.class, pageMock),
                instanceOf(TestPanel.class));
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
}
