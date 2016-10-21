package com.github.invictum.test.panels.init;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.init.PanelInitUtil;
import com.github.invictum.panels.strategy.NoWaitStrategy;
import com.github.invictum.test.panels.instances.PanelWithDisabledStrategy;
import com.github.invictum.test.panels.instances.TestPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class PanelInitUtilTest {

    @Mock
    private AbstractPage pageMock;

    @Mock
    private NoWaitStrategy strategyMock;

    @Before
    public void setupTest() throws Exception {
        Whitebox.setInternalState(PanelInitUtil.class, "strategy", strategyMock);
    }

    @Test
    public void applyGlobalInitStrategyTest() {
        PanelInitUtil.applyGlobalInitStrategy(TestPanel.class, pageMock);
        verify(strategyMock, times(1)).apply(pageMock);
    }

    @Test
    public void disabledGlobalInitStrategyTest() {
        PanelInitUtil.applyGlobalInitStrategy(PanelWithDisabledStrategy.class, pageMock);
        Mockito.verifyZeroInteractions(strategyMock);
    }
}
