package com.github.invictum.tricks;

import com.github.invictum.tricks.core.AbstractTrick;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.time.temporal.ChronoUnit;
import java.util.List;

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

    public boolean isElementVisible(By locator, WebElementFacade element, int timeout) {
        boolean isVisible;
        context().setImplicitTimeout(timeout, ChronoUnit.MILLIS);
        if (element == null) {
            isVisible = checkElementsVisibility(context().findAll(locator));
        } else {
            isVisible = checkElementsVisibility(element.thenFindAll(locator));
        }
        context().resetImplicitTimeout();
        return isVisible;
    }

    public boolean isElementVisible(By locator, WebElementFacade element) {
        return isElementVisible(locator, element, 500);
    }

    public boolean isElementVisible(By locator) {
        return isElementVisible(locator, null);
    }
}
