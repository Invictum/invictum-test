package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private final static Logger logger = LoggerFactory.getLogger("Logger");

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Object... objects) {
        logger.error(message, objects);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, Object... objects) {
        logger.info(message, objects);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void debug(String message, Object... objects) {
        logger.debug(message, objects);
    }
}
