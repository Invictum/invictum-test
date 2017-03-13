package com.github.invictum.allure.test.utils;

import com.github.invictum.allure.utils.AnnotationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.utils.AnnotationManager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class AnnotationUtilTest {

    @Test
    public void withTitleTest() {
        TestCaseStartedEvent event = new TestCaseStartedEvent(null, "testCaseNameNumber42");
        event = AnnotationUtil.withTitle(event);
        assertThat("Title formatted wrong.", event.getTitle(), equalTo("Test case name number 42"));
    }

    @Test
    public void withEssentialInfoTest() {
        TestCaseStartedEvent event = new TestCaseStartedEvent(null, null);
        TestCaseStartedEvent actualEvent = AnnotationUtil.withEssentialInfo(event);
        TestCaseStartedEvent expectedEvent = AnnotationManager.withExecutorInfo(event);
        assertThat("Essential info set wrong.", actualEvent.getLabels(), equalTo(expectedEvent.getLabels()));
    }
}
