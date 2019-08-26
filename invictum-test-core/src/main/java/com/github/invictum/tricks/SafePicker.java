package com.github.invictum.tricks;

import com.github.invictum.tricks.core.AbstractTrick;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;

/**
 * Allows to get Web Element text gracefully.
 * If Element Not Found exception occurs returns default value.
 */
public class SafePicker extends AbstractTrick {

    private String defaultValue = null;
    private int waitTimeout = 100;
    private WebElementFacade searchContext = null;

    private final static Logger LOG = LoggerFactory.getLogger(SafePicker.class);

    public SafePicker withDefaultValue(String value) {
        defaultValue = value;
        return this;
    }

    public SafePicker withTimeout(int timeout) {
        waitTimeout = timeout;
        return this;
    }

    public SafePicker onElement(WebElementFacade element) {
        searchContext = element;
        return this;
    }

    public SafePicker onPage() {
        searchContext = null;
        return this;
    }

    public String pick(By locator) {
        LOG.debug("Safe pick for {} with {} default value", locator, defaultValue);
        String resultValue = defaultValue;
        context().setImplicitTimeout(waitTimeout, ChronoUnit.MILLIS);
        try {
            WebElementFacade element = (searchContext == null) ? context().find(locator) : searchContext.find(locator);
            resultValue = element.getText();
            LOG.debug("Element located. Extracted text value: {}", resultValue);
        } catch (RuntimeException e) {
            LOG.debug("Element not found using default value");
        }
        context().resetImplicitTimeout();
        return resultValue;
    }

}
