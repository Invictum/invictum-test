package com.github.invictum.test.fixtures;

import com.github.invictum.fixtures.FixtureProcessor;
import com.github.invictum.utils.properties.EnhancedSystemProperty;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertiesUtil.class, FixtureProcessor.class})
public class FixtureProcessorTest {

    private HashMap<String, String> fixtures = new HashMap<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        mockStatic(PropertiesUtil.class);
        when(PropertiesUtil.getProperty(EnhancedSystemProperty.FixturesPackageName))
                .thenReturn("com.github.invictum.test.fixtures");
    }

    @Test
    public void prepareFixtureTest() throws Exception {
        fixtures.put("exampleFixture", "arg1, arg2");
        FixtureProcessor.apply(fixtures);
        assertThat("Fixture isn't registered.", FixtureProcessor.getRegisteredFixtures().size(), equalTo(1));
        assertThat("Applied wrong fixture.", FixtureProcessor.getRegisteredFixtures().peek().toString(),
                equalTo("ExampleFixture {arg1 arg2}"));
    }

    @Test
    public void rollbackFixtureTest() throws Exception {
        FixtureProcessor.rollback();
        assertThat("Fixture isn't rollback.", FixtureProcessor.getRegisteredFixtures().size(), equalTo(0));
    }
}
