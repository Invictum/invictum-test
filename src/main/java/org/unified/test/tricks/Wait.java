package org.unified.test.tricks;

import org.unified.test.Log;
import org.unified.test.tricks.core.AbstractTrick;

public class Wait extends AbstractTrick {

    public static final long MAX_WAIT_TIME = 10000;
    public static final long FACTOR = 2;

    public void waitForJquery(Object initiator) {
        long timeMark = System.currentTimeMillis();
        int factor = 0;
        while (factor < FACTOR) {
            context().waitABit(100);
            if ((Long) context().evaluateJavascript("return jQuery.active") == 0) {
                factor++;
                Log.debug("jQuery inactive. Factor decreased to {}", factor);
            } else {
                factor = 0;
                Log.debug("jQuery active. Factor set to {}", factor);
            }
            if (System.currentTimeMillis() - timeMark > MAX_WAIT_TIME) {
                Log.error("jQuery still active.");
                break;
            }
        }
        Log.debug("Smart wait for {} took {} ms", initiator, System.currentTimeMillis() - timeMark);
    }
}
