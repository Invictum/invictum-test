package com.github.invictum.tricks;

import com.github.invictum.tricks.core.AbstractTrick;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitElement extends AbstractTrick {

    private final static Logger LOG = LoggerFactory.getLogger(WaitElement.class);

    public static final long WAIT_TIMEOUT = 10000;
    public static final long STEP_TIMEOUT = 200;

    public void isVisible(By locator, long timeout, long step) {
        long timestamp = System.currentTimeMillis();
        LOG.debug("Initialized visibility wait at: {} for '{}'. Timeout: {} ms Step: {} ms", timestamp, locator,
                timeout, step);
        while (!context().getTrick(Visibility.class).isElementVisible(locator)) {
            context().waitABit(step);
            if (System.currentTimeMillis() - timestamp > timeout) {
                throw new IllegalStateException(String.format("Element invisible more than %s ms", timeout));
            }
            LOG.debug("Still invisible. Time spent {} ms", System.currentTimeMillis() - timestamp);
        }
        LOG.info("Finished wait for '{}'. Wait took {} ms", locator, System.currentTimeMillis() - timestamp);
    }

    public void isInvisible(By locator, long timeout, long step) {
        long timestamp = System.currentTimeMillis();
        LOG.debug("Initialized invisibility wait at: {} for '{}'. Timeout: {} ms Step: {} ms", timestamp, locator,
                timeout, step);
        while (context().getTrick(Visibility.class).isElementVisible(locator)) {
            context().waitABit(step);
            if (System.currentTimeMillis() - timestamp > timeout) {
                throw new IllegalStateException(String.format("Element visible more than %s ms", timeout));
            }
            LOG.debug("Still visible. Time spent {} ms", System.currentTimeMillis() - timestamp);
        }
        LOG.info("Finished wait for '{}'. Wait took {} ms", locator, System.currentTimeMillis() - timestamp);
    }

    public void isVisible(By locator, long timeout) {
        isVisible(locator, timeout, STEP_TIMEOUT);
    }

    public void isVisible(By locator) {
        isVisible(locator, WAIT_TIMEOUT);
    }

    public void isInvisible(By locator, long timeout) {
        isInvisible(locator, timeout, STEP_TIMEOUT);
    }

    public void isInvisible(By locator) {
        isInvisible(locator, WAIT_TIMEOUT);
    }
}
