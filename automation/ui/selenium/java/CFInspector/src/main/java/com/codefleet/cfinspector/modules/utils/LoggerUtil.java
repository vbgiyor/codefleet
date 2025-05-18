package com.codefleet.cfinspector.modules.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void info(String message) {
        String threadInfo = String.format("[Thread-%d]", Thread.currentThread().getId());
        logger.info("{} {}", threadInfo, message);
    }

    public static void error(String message, Throwable t) {
        String threadInfo = String.format("[Thread-%d]", Thread.currentThread().getId());
        logger.error("{} {}", threadInfo, message, t);
    }
}