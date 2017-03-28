package com.infinity.rxlogging;

import java.util.logging.Logger;

class DebugLog {
    private static Logger logger = Logger.getLogger(DebugLog.class.getName());

    DebugLog() {
    }

    /**
     * Send a debug log message
     *
     * @param tag     Source of a log message. It usually identifies the class or activity where the log
     *                call occurs.
     * @param message The message you would like logged.
     */
    public void log(String tag, String message) {
        logger.info(tag + message);
    }
}
