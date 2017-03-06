package com.github.invictum.test;

import com.github.invictum.Session;
import net.serenitybdd.core.Serenity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class SessionTest {

    @Before
    public void clearSession() {
        Serenity.getCurrentSession().clear();
    }

    @Test
    public void getDefaultTest() {
        Session.put("key", "string value");
        assertThat("Returned value is wrong.", Session.get("key"), equalTo("string value"));
    }

    @Test
    public void getTypedTest() {
        Object value = new Object();
        Session.put("key", value);
        assertThat("Returned type value is wrong.", Session.get("key", Object.class), equalTo(value));
    }

    @Test
    public void getUnexistingKeyTest() {
        assertThat("Non-existing key doesn't returned null.", Session.get("key", Object.class), equalTo(null));
    }

    @Test
    public void putGracefullyTest() {
        Session.put("myKey", "myValue");
        Session.putGracefully("myKey", "myValueNew");
        assertThat("Gracefully option was ignored.", Session.get("myKey"), equalTo("myValue"));
    }
}
