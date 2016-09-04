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
        String expected = "{textValue: name}";
        assertThat("Partially initialized object converted to string wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void toStringNullObjectTest() {
        String expected = "{null}";
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
    public void nestedDtoTest() {
        sut.setTextValue("some text");
        ExampleDto newDto = new ExampleDto();
        newDto.setTextValue("some inner text");
        sut.setDto(newDto);
        String expected = "{textValue: some text, dto: {textValue: some inner text}}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void hashTest() {
        sut.setTextValue("name");
        sut.setBoolValue(true);
        assertThat("Objects hash is wrong.", sut.hashCode(), equalTo(420606852));
    }

}
