package com.github.invictum;

import net.thucydides.core.steps.StepEventBus;
import org.junit.runner.Description;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.events.TestSuiteStartedEvent;
import ru.yandex.qatools.allure.utils.AnnotationManager;

public class AllureRunListener extends ru.yandex.qatools.allure.junit.AllureRunListener {

    @Override
    public void testSuiteStarted(Description description) {
        String uid = generateSuiteUid(description.getClassName());
        TestSuiteStartedEvent event = new TestSuiteStartedEvent(uid, description.getClassName());
        AnnotationManager manager = new AnnotationManager(description.getAnnotations());
        manager.update(event);
        event.withLabels(AllureModelUtils.createTestFrameworkLabel(StepEventBus.getEventBus().getTestSource()));
        getLifecycle().fire(event);
    }
}
