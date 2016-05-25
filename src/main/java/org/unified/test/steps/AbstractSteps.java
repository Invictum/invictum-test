package org.unified.test.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

public class AbstractSteps extends ScenarioSteps {

    public AbstractSteps() {
        super();
    }

    public AbstractSteps(Pages pages) {
        super(pages);
    }

    @Step
    public void description(String text) {
        // do nothing.
    }
}
