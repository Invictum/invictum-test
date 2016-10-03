package com.github.invictum.test.dto.attribute;

import com.github.invictum.dto.Attribute;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class AttributeTest {

    private Attribute first = null;

    @Before
    public void beforeTest() {
        first = new Attribute();
        first.setName("name");
        first.setValue("value");
    }

    @Test
    public void equalsTest() {
        assertThat("Attributes not equals.", first, equalTo(first));
    }

    @Test
    public void notEqualsTest() {
        Attribute second = new Attribute("name2", "value2");
        assertThat("Attributes are equals.", first, not(equalTo(second)));
    }

    @Test
    public void hashTest() {
        assertThat("Hash is wrong.", first.hashCode(), equalTo(1254553030));
    }

    @Test
    public void toStringTest() {
        String expected = "{name: name, value: value}";
        assertThat("String is wrong.", first.toString(), equalTo(expected));
    }

    @Test
    public void toStringNullTest() {
        first.setName(null);
        first.setValue(null);
        String expected = "{name: null, value: null}";
        assertThat("String is wrong.", first.toString(), equalTo(expected));
    }
}
