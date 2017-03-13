package com.github.invictum.allure.test.annotation;

import com.github.invictum.allure.annotation.AnnotationUtil;
import net.thucydides.core.model.Story;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.events.TestSuiteStartedEvent;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.utils.AnnotationManager;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void withClassTest() {
        TestSuiteStartedEvent event = new TestSuiteStartedEvent(null, null);
        List<Label> actualLabels = AnnotationUtil.withClass(event, Object.class).getLabels();
        List<Label> expectedLabels = new ArrayList<>();
        expectedLabels.add(new Label().withName("framework").withValue("JUNIT"));
        expectedLabels.add(new Label().withName("testClass").withValue("Object"));
        assertThat("Class labels is wrong.", actualLabels, equalTo(expectedLabels));
    }

    @Test
    public void withStoryTest() {
        TestSuiteStartedEvent event = new TestSuiteStartedEvent(null, null);
        Story story = new Story(null, "name", null, null, null);
        List<Label> actualLabels = AnnotationUtil.withStory(event, story).getLabels();
        List<Label> expectedLabels = new ArrayList<>();
        expectedLabels.add(new Label().withName("framework").withValue("BDD"));
        expectedLabels.add(new Label().withName("story").withValue("name"));
        expectedLabels.add(new Label().withName("feature").withValue("name"));
        assertThat("Class labels is wrong.", actualLabels, equalTo(expectedLabels));
    }
}
