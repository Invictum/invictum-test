package com.github.invictum.test.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DtoTest {

    private ExampleDto sut = null;

    @Before
    public void initTestDto() {
        sut = new ExampleDto();
    }

    @Test
    public void getterSetterTest() {
        sut.setName("name");
        assertThat("Getter returned incorrect result.", sut.getName(), equalTo("name"));
    }

    @Test
    public void toStringTest() {
        sut.setName("name");
        sut.setValue("1");
        sut.setTestValue("2");
        String expected = "{name: name, value: 1, testValue: 2}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void toStringPartialInitTest() {
        sut.setName("name");
        String expected = "{name: name}";
        assertThat("Partially inited object converted to string wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void toStringNullObjectTest() {
        String expected = "{null}";
        assertThat("Null object converted to string wrong..", sut.toString(), equalTo(expected));
    }

    @Test
    public void equalsTest() {
        ExampleDto expected = new ExampleDto();
        expected.setName("name");
        expected.setValue("value");
        sut.setName("name");
        sut.setValue("value");
        assertThat("Objects aren't equal.", expected, equalTo(sut));
    }

    @Test
    public void notEqualsTest() {
        ExampleDto expected = new ExampleDto();
        expected.setName("name");
        expected.setValue("value");
        sut.setName("name1");
        sut.setValue("value1");
        assertThat("Objects are equal.", expected, not(equalTo(sut)));
    }

    @Test
    public void partialEqualTest() {
        ExampleDto expected = new ExampleDto();
        expected.setName("name");
        sut.setName("name");
        sut.setValue("value");
        assertThat("Objects aren't equal.", expected, equalTo(sut));
    }

    @Test
    public void partialNotEqualTest() {
        ExampleDto expected = new ExampleDto();
        expected.setName("name");
        expected.setName("value");
        sut.setValue("value");
        assertThat("Objects aren't equal.", expected, not(equalTo(sut)));
    }

    @Test
    public void hashTest() {
        sut.setName("name");
        sut.setValue("value");
        assertThat("Objects hash is wrong.", sut.hashCode(), equalTo(-528070181));
    }
}
