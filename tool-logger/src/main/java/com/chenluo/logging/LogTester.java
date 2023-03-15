package com.chenluo.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTester {
    private final Logger logger = LogManager.getLogger(LogTester.class);

    public static void main(String[] args) {
        new LogTester().tryLog();

    }

    private void tryLog() {

        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        logger.error("error with exception", new RuntimeException("exception message"));
    }
}
