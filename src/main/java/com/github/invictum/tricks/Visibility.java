package com.github.invictum.tricks;

import net.serenitybdd.core.pages.WebElementFacade;
import com.github.invictum.tricks.core.AbstractTrick;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Visibility extends AbstractTrick {

    private boolean checkElementsVisibility(List<WebElementFacade> elements) {
        boolean result = false;
        for (WebElementFacade element : elements) {
            if (element.isVisible()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isElementVisible(String xpath, WebElementFacade element) {
        boolean isVisible;
        context().setImplicitTimeout(500, TimeUnit.MILLISECONDS);
        if (element == null) {
            isVisible = checkElementsVisibility(context().findAll(xpath));
        } else {
            isVisible = checkElementsVisibility(element.thenFindAll(xpath));
        }
        context().resetImplicitTimeout();
        return isVisible;
    }
}
