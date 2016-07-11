package com.github.invictum.tricks;

import com.github.invictum.tricks.core.AbstractTrick;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Allows to determinate Web Element visibility gracefully.
 */
public class Visibility extends AbstractTrick {

    private boolean checkElementsVisibility(List<WebElementFacade> elements) {
        for (WebElementFacade element : elements) {
            if (element.isVisible()) {
                return true;
            }
        }
        return false;
    }

    public boolean isElementVisible(String xpath, WebElementFacade element, int timeout) {
        boolean isVisible;
        context().setImplicitTimeout(timeout, TimeUnit.MILLISECONDS);
        if (element == null) {
            isVisible = checkElementsVisibility(context().findAll(xpath));
        } else {
            isVisible = checkElementsVisibility(element.thenFindAll(xpath));
        }
        context().resetImplicitTimeout();
        return isVisible;
    }

    public boolean isElementVisible(String xpath, WebElementFacade element) {
        return isElementVisible(xpath, element, 500);
    }

    public boolean isElementVisible(String xpath) {
        return isElementVisible(xpath, null);
    }
}
