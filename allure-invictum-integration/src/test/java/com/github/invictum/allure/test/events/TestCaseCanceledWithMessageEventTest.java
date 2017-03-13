package com.github.invictum.allure.test.events;

import com.github.invictum.allure.events.TestCaseCanceledWithMessageEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class TestCaseCanceledWithMessageEventTest {

    @Test
    public void defaultMessageTest() {
        TestCaseCanceledWithMessageEvent event = new TestCaseCanceledWithMessageEvent();
        assertThat("Default message is wrong.", event.getMessage(), equalTo("Test skipped with unknown reason"));
    }

    @Test
    public void customMessageTest() {
        TestCaseCanceledWithMessageEvent event = new TestCaseCanceledWithMessageEvent().withMessage("Custom message");
        assertThat("Default message is wrong.", event.getMessage(), equalTo("Custom message"));
    }
}
