package com.github.invictum.steps;

import com.github.invictum.utils.SoftAssertsUtil;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import org.assertj.core.api.SoftAssertions;

public class AbstractSteps extends ScenarioSteps {

    public AbstractSteps() {
        super();
    }

    public AbstractSteps(Pages pages) {
        super(pages);
    }

    public SoftAssertions softAssertions() {
        return SoftAssertsUtil.instance();
    }

    @Step
    public void description(String text) {
        // do nothing.
    }

    @Step
    public void assertAllSoftly() {
        SoftAssertsUtil.assertAllSoftly();
    }
}
