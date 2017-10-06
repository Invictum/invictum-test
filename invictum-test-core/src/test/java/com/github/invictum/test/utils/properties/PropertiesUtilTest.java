package com.github.invictum.test.utils.properties;

import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.apache.commons.configuration.CompositeConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PropertiesUtil.class)
public class PropertiesUtilTest {

    private CompositeConfiguration mock = Mockito.mock(CompositeConfiguration.class);

    @Before
    public void beforeTest() throws Exception {
        PowerMockito.whenNew(CompositeConfiguration.class).withAnyArguments().thenReturn(mock);
    }

    @Test
    public void getPropertyByKeyTest() throws Exception {
        PowerMockito.when(mock, "getString", "key").thenReturn("test");
        assertThat("Returned wrong property.", PropertiesUtil.getProperty("key"), equalTo("test"));
    }

    @Test
    public void getAbsentPropertyAsListTest() throws Exception {
        List<String> expected = new ArrayList<>();
        assertThat("Returned wrong list property.", PropertiesUtil
                .getPropertyAsList(EnhancedSystemProperty.AllureEnvironmentCustomProperties), equalTo(expected));
    }
}
