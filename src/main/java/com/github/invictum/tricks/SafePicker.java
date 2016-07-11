package com.github.invictum.tricks;

import com.github.invictum.tricks.core.AbstractTrick;
import net.serenitybdd.core.pages.WebElementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Allows to get Web Element text gracefully.
 * If Element Not Found exception occurs returns default value.
 */
public class SafePicker extends AbstractTrick {

    private final static Logger LOG = LoggerFactory.getLogger(SafePicker.class);

    public String pick(String locator, String defaultValue, int waitTimeout) {
        LOG.debug("Safe pick for {} with {} default value", locator, defaultValue);
        String resultValue = defaultValue;
        context().setImplicitTimeout(waitTimeout, TimeUnit.MILLISECONDS);
        try {
            WebElementFacade element = context().findBy(locator);
            resultValue = element.getText();
            LOG.debug("Element located. Extracted text value: {}", resultValue);
        } catch (RuntimeException e) {
            LOG.debug("Element not found using default value");
        }
        context().resetImplicitTimeout();
        return resultValue;
    }

    public String pick(String locator, String defaultValue) {
        return pick(locator, defaultValue, 100);
    }

    public String pick(String locator) {
        return pick(locator, null, 100);
    }
}
