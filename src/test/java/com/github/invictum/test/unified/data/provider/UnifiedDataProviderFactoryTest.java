package com.github.invictum.test.unified.data.provider;

import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderFactory;
import com.github.invictum.unified.data.provider.parsers.YamlParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UnifiedDataProviderFactory.class)
public class UnifiedDataProviderFactoryTest {

    private static UnifiedDataProvider dataProvider = null;
    private YamlParser mockParser = mock(YamlParser.class);

    @BeforeClass
    public static void beforeClass() {
        dataProvider = new UnifiedDataProvider();
        dataProvider.setName("Name");
        dataProvider.setBase("base");
    }

    @Before
    public void beforeTest() {
        UnifiedDataProviderFactory.restCache();
        when(mockParser.load("Object")).thenReturn(dataProvider);
        UnifiedDataProviderFactory.setParser(mockParser);
    }

    @Test
    public void getInstanceTest() {
        UnifiedDataProvider actual = UnifiedDataProviderFactory.getInstance(new Object());
        assertThat("Returned provider is wrong.", actual, equalTo(dataProvider));
        verify(mockParser, times(1)).load("Object");
    }

    @Test
    public void getInstanceFromCacheTest() {
        /** Get instance twice to invoke cache. */
        UnifiedDataProviderFactory.getInstance(new Object());
        UnifiedDataProviderFactory.getInstance(new Object());
        /** Two requests but only one load. */
        verify(mockParser, times(1)).load("Object");
    }
}
