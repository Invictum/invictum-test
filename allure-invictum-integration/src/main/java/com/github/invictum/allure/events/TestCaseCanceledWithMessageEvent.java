package com.github.invictum.allure.events;

import ru.yandex.qatools.allure.events.TestCaseCanceledEvent;

public class TestCaseCanceledWithMessageEvent extends TestCaseCanceledEvent {

    private String message;

    @Override
    public String getMessage() {
        return message == null ? super.getMessage() : message;
    }

    public TestCaseCanceledWithMessageEvent withMessage(String message) {
        this.message = message;
        return this;
    }
}
