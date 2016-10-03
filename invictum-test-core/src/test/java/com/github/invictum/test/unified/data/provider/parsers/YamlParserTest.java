package com.github.invictum.test.unified.data.provider.parsers;

import com.github.invictum.unified.data.provider.UnifiedDataProvider;
import com.github.invictum.unified.data.provider.parsers.Parser;
import com.github.invictum.unified.data.provider.parsers.YamlParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class YamlParserTest {

    @Test
    public void resourceAbsentTest() {
        Parser parser = new YamlParser();
        UnifiedDataProvider actual = parser.load("invalidFileName");
        assertThat("Default object isn't returned.", actual, equalTo(new UnifiedDataProvider()));
    }
}
