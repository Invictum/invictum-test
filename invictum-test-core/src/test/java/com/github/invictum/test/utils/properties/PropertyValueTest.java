package com.github.invictum.test.utils.properties;

import com.github.invictum.utils.properties.PropertyValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class PropertyValueTest {

    @Test
    public void asIntegerTest() {
        assertThat("Integer converted wrong.", new PropertyValue("42").asInteger(), equalTo(42));
    }

    @Test
    public void asIntegerDefaultTest() {
        assertThat("Integer converted wrong.", new PropertyValue(null).asInteger(), equalTo(0));
    }

    @Test
    public void asBooleanTest() {
        assertThat("Boolean converted wrong.", new PropertyValue("true").asBoolean(), equalTo(true));
    }

    @Test
    public void asArrayTest() {
        PropertyValue value = new PropertyValue(" value1,  value2, value3  ");
        assertThat("Array converted wrong.", value.asArray(), equalTo(new String[]{"value1", "value2", "value3"}));
    }
}
