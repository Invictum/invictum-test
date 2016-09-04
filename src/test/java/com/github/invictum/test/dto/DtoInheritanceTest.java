package com.github.invictum.test.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DtoInheritanceTest {

    private ExampleInheritedDTO sut = null;

    @Before
    public void initTestDto() {
        sut = new ExampleInheritedDTO();
    }

    @Test
    public void mainDtoTest() {
        sut.setText1("Text 1");
        sut.setText2("Text 2");
        String expected = "{text1: Text 1, text2: Text 2}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void inheritedDtoTest() {
        sut.setTextValue("Some text");
        sut.setBoolValue(true);
        String expected = "{textValue: Some text, boolValue: true}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void bothDtoTest() {
        sut.setTextValue("Some text");
        sut.setText1("Text 1");
        String expected = "{text1: Text 1, textValue: Some text}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void equalsInheritedDtoTest() {
        sut.setTextValue("Some text");
        sut.setText1("Text 1");
        ExampleInheritedDTO expected = new ExampleInheritedDTO();
        expected.setTextValue("Some text");
        expected.setText1("Text 1");
        assertThat("Objects aren't equal.", sut, equalTo(expected));
    }

    @Test(expected = AssertionError.class)
    public void notEqualsInheritedDtoTest() {
        sut.setTextValue("Some text");
        sut.setText1("Text 1");
        ExampleInheritedDTO expected = new ExampleInheritedDTO();
        expected.setTextValue("Another Text");
        expected.setText1("Text 1");
        assertThat("Objects aren't equal.", sut, equalTo(expected));
    }

    @Test
    public void hashTest() {
        sut.setTextValue("Some text");
        sut.setText1("Text 2");
        assertThat("Objects hash is wrong.", sut.hashCode(), equalTo(-62715168));
    }
}
