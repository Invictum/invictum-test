package com.github.invictum.test.pages;

import com.github.invictum.pages.AbstractPage;
import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.utils.url.EnhancedPageUrls;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyObject;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractPage.class, UnifiedDataProviderFactory.class})
public class AbstractPageTest {

    @BeforeClass
    public static void setUp() throws Exception {
        EnhancedPageUrls urlsMock = mock(EnhancedPageUrls.class);
        whenNew(EnhancedPageUrls.class).withAnyArguments().thenReturn(urlsMock);

        mockStatic(UnifiedDataProviderFactory.class);
        UnifiedDataProvider dataProvider = new UnifiedDataProvider();
        when(UnifiedDataProviderFactory.class, "getInstance", anyObject()).thenReturn(dataProvider);
    }

    @Test
    public void toStringTest() {
        AbstractPage page = new AbstractPage();
        assertThat("To string method value is wrong.", page.toString(), equalTo("AbstractPage"));
    }
}
