package com.github.invictum.test.panels;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.panels.AbstractPanel;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnifiedDataProviderFactory.class})
public class AbstractPanelTest {

    private AbstractPage pageMock = mock(AbstractPage.class);
    private WebElementFacade panelMock = mock(WebElementFacadeImpl.class);

    @Before
    public void setupTest() throws Exception {
        mockStatic(UnifiedDataProviderFactory.class);
        UnifiedDataProvider dataProvider = new UnifiedDataProvider();
        Map<String, String> locators = new HashMap<>();
        locators.put("xpath", "//div");
        locators.put("jquery", "jquery = div.class:visible");
        dataProvider.setLocators(locators);
        when(UnifiedDataProviderFactory.class, "getInstance", Matchers.anyObject()).thenReturn(dataProvider);
    }

    @Test
    public void toStringTest() {
        AbstractPanel panel = new AbstractPanel();
        assertThat("To string method value is wrong.", panel.toString(), equalTo("AbstractPanel"));
    }

    @Test
    @Ignore
    public void locateTest() {
        AbstractPanel panel = new AbstractPanel();
        panel.initWith(pageMock, panelMock);
        panel.locate("xpath");
        verify(panelMock, times(1)).find(By.xpath(".//div"));
    }

    @Test
    @Ignore
    public void locateJqueryTest() {
        AbstractPanel panel = new AbstractPanel();
        when(pageMock.activateIfJQueryRelated(By.jquery("div.class:visible"))).thenReturn(true);
        panel.initWith(pageMock, panelMock);
        panel.locate("jquery");
        verify(pageMock, times(1)).find(By.jquery("div.class:visible"));
    }

    @Test
    @Ignore
    public void locateAllTest() {
        AbstractPanel panel = new AbstractPanel();
        panel.initWith(pageMock, panelMock);
        panel.locateAll("xpath");
        verify(panelMock, times(1)).thenFindAll(By.xpath(".//div"));
    }

    @Test
    @Ignore
    public void locateAllJqueryTest() {
        AbstractPanel panel = new AbstractPanel();
        when(pageMock.activateIfJQueryRelated(By.jquery("div.class:visible"))).thenReturn(true);
        panel.initWith(pageMock, panelMock);
        panel.locateAll("jquery");
        verify(pageMock, times(1)).findAll(By.jquery("div.class:visible"));
    }
}
