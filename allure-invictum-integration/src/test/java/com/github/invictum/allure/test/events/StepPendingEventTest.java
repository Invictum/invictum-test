package com.github.invictum.allure.test.events;

import com.github.invictum.allure.events.StepPendingEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.yandex.qatools.allure.model.Step;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.allure.model.Status.PENDING;

@RunWith(JUnit4.class)
public class StepPendingEventTest {

    @Test
    public void processTest() {
        Step context = new Step();
        new StepPendingEvent().process(context);
        assertThat("Pending step status updated wrong.", context.getStatus(), equalTo(PENDING));
    }
}
