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
        sut.setTextValue("name");
        assertThat("Getter returned incorrect result.", sut.getTextValue(), equalTo("name"));
    }

    @Test
    public void toStringTest() {
        sut.setTextValue("name");
        sut.setIntValue(1);
        sut.setBoolValue(true);
        String expected = "{textValue: name, boolValue: true, intValue: 1}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void toStringPartialInitTest() {
        sut.setTextValue("name");
        String expected = "{textValue: name, intValue: 0}";
        assertThat("Partially inited object converted to string wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void toStringNullObjectTest() {
        String expected = "{intValue: 0}";
        assertThat("Null object converted to string wrong..", sut.toString(), equalTo(expected));
    }

    @Test
    public void equalsTest() {
        ExampleDto expected = new ExampleDto();
        expected.setTextValue("name");
        expected.setIntValue(100);
        sut.setTextValue("name");
        sut.setIntValue(100);
        assertThat("Objects aren't equal.", sut, equalTo(expected));
    }

    @Test
    public void notEqualsTest() {
        ExampleDto expected = new ExampleDto();
        expected.setTextValue("name");
        expected.setBoolValue(true);
        sut.setTextValue("name1");
        sut.setBoolValue(false);
        assertThat("Objects are equal.", sut, not(equalTo(expected)));
    }

    @Test
    public void partialEqualTest() {
        ExampleDto expected = new ExampleDto();
        expected.setTextValue("name");
        sut.setTextValue("name");
        sut.setBoolValue(true);
        assertThat("Objects aren't equal.", sut, equalTo(expected));
    }

    @Test
    public void partialNotEqualTest() {
        ExampleDto expected = new ExampleDto();
        expected.setTextValue("name");
        expected.setBoolValue(true);
        sut.setTextValue("value");
        assertThat("Objects aren't equal.", sut, not(equalTo(expected)));
    }

    @Test
    public void hashTest() {
        sut.setTextValue("name");
        sut.setBoolValue(true);
        assertThat("Objects hash is wrong.", sut.hashCode(), equalTo(1456003847));
    }
}
