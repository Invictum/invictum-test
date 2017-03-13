package com.github.invictum.allure.utils;

import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.utils.AnnotationManager;

import static org.apache.commons.lang3.StringUtils.*;

public class AnnotationUtil {

    public static TestCaseStartedEvent withEssentialInfo(TestCaseStartedEvent event) {
        return AnnotationManager.withExecutorInfo(event);
    }

    public static TestCaseStartedEvent withTitle(TestCaseStartedEvent event) {
        String title = capitalize(lowerCase(join(splitByCharacterTypeCamelCase(event.getName()), SPACE)));
        return event.withTitle(title);
    }
}
