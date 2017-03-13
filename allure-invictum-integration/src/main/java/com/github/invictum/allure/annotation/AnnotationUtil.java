package com.github.invictum.allure.annotation;

import net.thucydides.core.model.Story;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.events.TestSuiteStartedEvent;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.utils.AnnotationManager;

import static com.github.invictum.allure.annotation.TestFramework.BDD;
import static com.github.invictum.allure.annotation.TestFramework.JUNIT;
import static org.apache.commons.lang3.StringUtils.*;
import static ru.yandex.qatools.allure.config.AllureModelUtils.*;

public class AnnotationUtil {

    private static Label createTestFrameworkLabel(TestFramework testFramework) {
        return AllureModelUtils.createTestFrameworkLabel(testFramework.toString());
    }

    public static TestCaseStartedEvent withEssentialInfo(TestCaseStartedEvent event) {
        return AnnotationManager.withExecutorInfo(event);
    }

    public static TestCaseStartedEvent withTitle(TestCaseStartedEvent event) {
        String title = capitalize(lowerCase(join(splitByCharacterTypeCamelCase(event.getName()), SPACE)));
        return event.withTitle(title);
    }

    public static TestSuiteStartedEvent withClass(TestSuiteStartedEvent event, Class<?> storyClass) {
        return event.withLabels(createTestFrameworkLabel(JUNIT), createTestClassLabel(storyClass.getSimpleName()));
    }

    public static TestSuiteStartedEvent withStory(TestSuiteStartedEvent event, Story story) {
        return event
                .withLabels(createTestFrameworkLabel(BDD), createStoryLabel(story.getName()), createFeatureLabel(story
                        .getName()));
    }
}
