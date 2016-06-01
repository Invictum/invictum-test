package com.github.invictum.test.unified.data.provider;

import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.UnifiedDataProviderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class UnifiedDataProviderUtilTest {

    private UnifiedDataProvider dataProvider = null;

    @Before
    public void beforeTest() {
        dataProvider = new UnifiedDataProvider();
    }

    @Test
    public void getExistedDataTest() {
        Map<String, String> data = new HashMap<>();
        data.put("dataKey", "value");
        dataProvider.setData(data);
        String actual = UnifiedDataProviderUtil.getDataByKey("dataKey", dataProvider);
        assertThat("Data value is wrong.", actual, equalTo("value"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNonExistedDataTest() {
        dataProvider.setData(new HashMap<String, String>());
        UnifiedDataProviderUtil.getLocatorByKey("dataKey", dataProvider);
    }

    @Test
    public void getExistedLocatorTest() {
        Map<String, String> locators = new HashMap<>();
        locators.put("locatorKey", "locatorValue");
        dataProvider.setLocators(locators);
        String actual = UnifiedDataProviderUtil.getLocatorByKey("locatorKey", dataProvider);
        assertThat("Locator value is wrong.", actual, equalTo("locatorValue"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNonExistedLocatorTest() {
        dataProvider.setLocators(new HashMap<String, String>());
        UnifiedDataProviderUtil.getLocatorByKey("locatorKey", dataProvider);
    }
}
