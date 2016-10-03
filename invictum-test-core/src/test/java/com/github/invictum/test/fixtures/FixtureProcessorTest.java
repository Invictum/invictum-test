package com.github.invictum.test.fixtures;

import com.github.invictum.fixtures.FixtureProcessor;
import com.github.invictum.utils.properties.PropertiesUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static com.github.invictum.utils.properties.EnhancedSystemProperty.FixturesPackageName;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertiesUtil.class, FixtureProcessor.class})
public class FixtureProcessorTest {

    private HashMap<String, String> fixtures = new HashMap<>();

    @Mock
    private ExampleFixture fixtureMock;

    @BeforeClass
    public static void beforeClass() throws Exception {
        mockStatic(PropertiesUtil.class);
        when(PropertiesUtil.getProperty(FixturesPackageName)).thenReturn("com.github.invictum.test.fixtures");
    }

    @Test
    public void prepareFixtureTest() throws Exception {
        whenNew(ExampleFixture.class).withNoArguments().thenReturn(fixtureMock);
        fixtures.put("exampleFixture", "arg1, arg2");
        FixtureProcessor.apply(fixtures);
        verifyNew(ExampleFixture.class, Mockito.times(1));
        assertThat("Fixture isn't registered.", FixtureProcessor.getRegisteredFixtures().size(), equalTo(1));
    }

    @Test
    public void rollbackFixtureTest() throws Exception {
        FixtureProcessor.rollback();
        assertThat("Fixture isn't rollback.", FixtureProcessor.getRegisteredFixtures().size(), equalTo(0));
    }
}
