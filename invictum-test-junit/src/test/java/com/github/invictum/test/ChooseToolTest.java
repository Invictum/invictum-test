package com.github.invictum.test;

import com.github.invictum.velocity.ChooseTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ChooseToolTest {

    private ChooseTool sut = new ChooseTool();

    @Test
    public void chooseIntTest() {
        Integer[] list = {1, 2, 3};
        int actual = sut.one(list);
        assertThat("Integer is chosen wrong.", Arrays.asList(list).contains(actual));
    }

    @Test
    public void chooseStringTest() {
        String[] list = {"A", "B", "C"};
        String actual = sut.one(list);
        assertThat("String is chosen wrong.", Arrays.asList(list).contains(actual));
    }

    @Test
    public void chooseObjectTest() {
        Object[] list = {new Object(), new Object()};
        Object actual = sut.one(list);
        assertThat("Object is chosen wrong.", Arrays.asList(list).contains(actual));
    }
}
