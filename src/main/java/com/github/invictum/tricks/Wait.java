package com.github.invictum.tricks;

import com.github.invictum.tricks.core.AbstractTrick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wait extends AbstractTrick {

    private final static Logger LOG = LoggerFactory.getLogger(Wait.class);

    public static final long MAX_WAIT_TIME = 10000;
    public static final long FACTOR = 2;

    public void waitForJquery(Object initiator, int staticWait, int step) {
        context().waitABit(staticWait);
        long timeMark = System.currentTimeMillis();
        int factor = 0;
        while (factor < FACTOR) {
            context().waitABit(step);
            if ((Long) context().evaluateJavascript("return jQuery.active") == 0) {
                factor++;
                LOG.debug("jQuery inactive. Factor decreased to {}", factor);
            } else {
                factor = 0;
                LOG.debug("jQuery active. Factor set to {}", factor);
            }
            if (System.currentTimeMillis() - timeMark > MAX_WAIT_TIME) {
                LOG.error("jQuery still active.");
                break;
            }
        }
        LOG.debug("Smart wait for {} took {} ms", initiator, System.currentTimeMillis() - timeMark);
    }

    public void waitForJquery(Object initiator, int staticWait) {
        waitForJquery(initiator, staticWait, 100);
    }

    public void waitForJquery() {
        waitForJquery(context(), 0);
    }

    public void waitForJquery(Object initiator) {
        waitForJquery(initiator, 0, 100);
    }
}
