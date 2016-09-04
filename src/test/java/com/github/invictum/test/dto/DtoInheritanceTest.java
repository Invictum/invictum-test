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
        sut.setText1("Text1");
        sut.setText2("Text2");
        String expected = "{text1: Text1, text2: Text2}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void inheritedDtoTest() {
        sut.setName("Name");
        sut.setValue("Value");
        String expected = "{name: Name, value: Value}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void bothDtoTest() {
        sut.setName("Name");
        sut.setText1("Text");
        String expected = "{text1: Text, name: Name}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void equalsInheritedDtoTest() {
        sut.setName("Name");
        sut.setText1("Text");
        ExampleInheritedDTO expected = new ExampleInheritedDTO();
        expected.setName("Name");
        expected.setText1("Text");
        assertThat("Objects aren't equal.", sut, equalTo(expected));
    }

    @Test(expected = AssertionError.class)
    public void notEqualsInheritedDtoTest() {
        sut.setName("Name");
        sut.setText1("Text");
        ExampleInheritedDTO expected = new ExampleInheritedDTO();
        expected.setName("AnotherName");
        expected.setText1("Text");
        assertThat("Objects aren't equal.", sut, equalTo(expected));
    }

    @Test
    public void hashTest() {
        sut.setName("Name");
        sut.setText1("Text2");
        assertThat("Objects hash is wrong.", sut.hashCode(), equalTo(2103926808));
    }
}
