package com.github.invictum.test.fixtures;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class FixtureTest {

    private ExampleFixture fixture = null;

    @Before
    public void beforeTest() {
        fixture = new ExampleFixture();
    }

    @Test
    public void paramTest() {
        fixture.setParams(new String[]{"param1", "param2"});
        assertThat("The first param is wrong.", fixture.getParam(0), equalTo("param1"));
        assertThat("The second param is wrong.", fixture.getParam(1), equalTo("param2"));
    }

    @Test
    public void paramAbsentTest() {
        fixture.setParams(new String[]{"param"});
        assertThat("Parameter value with non-existing index is wrong.", fixture.getParam(1), equalTo(null));
    }

    @Test
    public void parametersTest() {
        List<String> list = new ArrayList<>();
        list.add("param1");
        list.add("param2");
        String[] array = new String[list.size()];
        fixture.setParams(list.toArray(array));
        assertThat("Parameters list is wrong.", fixture.parameters(), equalTo(list));
    }

    @Test
    public void toStringTest() {
        fixture.setParams(new String[]{"param1", "param2"});
        assertThat("Fixture name is wrong.", fixture.toString(), equalTo("ExampleFixture {param1 param2}"));
    }

    @Test
    public void toStringNoParamTest() {
        assertThat("Fixture name is wrong.", fixture.toString(), equalTo("ExampleFixture"));
    }
}
