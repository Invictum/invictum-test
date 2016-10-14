package com.github.invictum.test.unified.data.provider.parsers;

import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.parsers.Parser;
import com.github.invictum.unified.data.provider.parsers.YamlParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class YamlParserTest {

    private Parser parser = new YamlParser();

    @Test
    public void resourceAbsentTest() {
        UnifiedDataProvider actual = parser.load("invalidFileName");
        assertThat("Default object isn't returned.", actual, equalTo(new UnifiedDataProvider()));
    }

    @Test
    public void wrongExtensionTest() {
        UnifiedDataProvider actual = parser.load("wrong-resource");
        assertThat("Default object isn't returned.", actual, equalTo(new UnifiedDataProvider()));
    }

    @Test
    public void resourceLoadTest() {
        UnifiedDataProvider expected = new UnifiedDataProvider();
        expected.setName("Test resource");
        expected.setBase("//base");
        expected.setRelatedClassName("testClass");
        Map<String, String> urls = new HashMap<>();
        urls.put("default", "default");
        expected.setUrls(urls);
        Map<String, String> locators = new HashMap<>();
        locators.put("locator", "//div");
        expected.setLocators(locators);
        Map<String, String> data = new HashMap<>();
        data.put("data", "data");
        expected.setData(data);
        UnifiedDataProvider actual = parser.load("test-resource");
        assertThat("Data is parsed wrong.", actual, equalTo(expected));
    }
}
