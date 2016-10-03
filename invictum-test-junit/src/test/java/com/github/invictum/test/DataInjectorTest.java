package com.github.invictum.test;

import com.github.invictum.data.injector.DataInjector;
import com.github.invictum.test.data.BadInjectableObject;
import com.github.invictum.test.data.InjectableObject;
import com.github.invictum.test.data.TestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DataInjectorTest {

    private InjectableObject sut = null;

    @Before
    public void initTestDto() {
        sut = new InjectableObject();
    }

    @Test
    public void injectSingleDtoTest() {
        TestDto expected = new TestDto("String", 42);
        DataInjector.injectInto(sut);
        TestDto actual = sut.getData();
        assertThat("Single DTO injected wrong.", actual, equalTo(expected));
    }

    @Test
    public void injectWithVelocityTest() {
        DataInjector.injectInto(sut);
        TestDto actual = sut.getVelocityData();
        assertThat("Integer value is wrong.", actual.getInteger(), equalTo(20));
        assertThat("Generated string is wrong.", actual.getString().matches("^[\\w]{10}$"));
    }

    @Test
    public void injectListDtoTest() {
        List<TestDto> expectedList = new ArrayList<>();
        expectedList.add(new TestDto("String", 42));
        expectedList.add(new TestDto("StringTwo", 44));
        DataInjector.injectInto(sut);
        List<TestDto> actualList = sut.getListData();
        assertThat("DTO list injected wrong.", actualList, equalTo(expectedList));
    }

    @Test(expected = IllegalStateException.class)
    public void wrongResourceTest() {
        BadInjectableObject injectableObject = new BadInjectableObject();
        DataInjector.injectInto(injectableObject);
    }
}
