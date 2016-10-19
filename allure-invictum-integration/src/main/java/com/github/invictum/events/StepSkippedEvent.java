package com.github.invictum.events;

import ru.yandex.qatools.allure.events.AbstractStepCanceledEvent;
import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.model.Step;

public class StepSkippedEvent extends AbstractStepCanceledEvent {

    @Override
    public void process(Step context) {
        context.setStatus(Status.SKIPPED);
    }
}
