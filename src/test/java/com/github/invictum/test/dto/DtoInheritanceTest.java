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
        sut.setText1("text1");
        sut.setText2("text2");
        String expected = "{text1: text1, text2: text2, intValue: 0}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void inheritedDtoTest() {
        sut.setTextValue("text");
        sut.setBoolValue(true);
        String expected = "{textValue: text, boolValue: true, intValue: 0}";
        assertThat("To string method proceed wrong.", sut.toString(), equalTo(expected));
    }

    @Test
    public void bothDtoTest() {
        sut.setText1("text1");
        sut.setTextValue("text2");
    }

    @Test
    public void hashTest() {
        sut.setTextValue("text1");
        sut.setText1("text2");
        assertThat("Objects hash is wrong.", sut.hashCode(), equalTo(-617520364));
    }

}
