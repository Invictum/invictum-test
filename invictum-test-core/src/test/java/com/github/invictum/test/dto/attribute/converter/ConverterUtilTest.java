package com.github.invictum.test.dto.attribute.converter;

import com.github.invictum.dto.Attribute;
import com.github.invictum.test.dto.instances.EnumAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.invictum.dto.attribute.converter.ConverterUtil.convert;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class ConverterUtilTest {

    @Test
    public void convertNullTest() {
        assertThat("Null is converted wrong.", convert(null), equalTo(null));
    }

    @Test
    public void convertStringTest() {
        assertThat("String is converted wrong.", convert("String"), equalTo("String"));
    }

    @Test
    public void convertIntegerTest() {
        assertThat("integer is converted wrong.", convert(42), equalTo("42"));
    }

    @Test
    public void convertBooleanTest() {
        assertThat("Boolean is converted wrong.", convert(false), equalTo("false"));
    }

    @Test
    public void convertFloatTest() {
        assertThat("Float is converted wrong.", convert(3.14f), equalTo("3.14"));
    }

    @Test
    public void convertDoubleTest() {
        assertThat("Double is converted wrong.", convert(0.128), equalTo("0.128"));
    }

    @Test
    public void convertEnumTest() {
        assertThat("Enum is converted wrong.", convert(EnumAttribute.ALPHA), equalTo("ALPHA"));
    }

    @Test
    public void convertUnknownTest() {
        assertThat("Unknown type is converted wrong.", convert(new Attribute("key", "value")), equalTo(null));
    }

    @Test
    public void convertListTest() {
        List<String> candidate = new ArrayList<>();
        candidate.add("one");
        candidate.add("two");
        assertThat("List is converted wrong.", convert(candidate), equalTo("[one, two]"));
    }

    @Test
    public void convertListWithNullTest() {
        List<String> candidate = new ArrayList<>();
        candidate.add("one");
        candidate.add(null);
        candidate.add("two");
        assertThat("List is converted wrong.", convert(candidate), equalTo("[one, null, two]"));
    }

    @Test
    public void convertSetTest() {
        Set<String> candidate = new HashSet<>();
        candidate.add("1");
        candidate.add("2");
        assertThat("Set is converted wrong.", convert(candidate), equalTo("[1, 2]"));
    }
}
