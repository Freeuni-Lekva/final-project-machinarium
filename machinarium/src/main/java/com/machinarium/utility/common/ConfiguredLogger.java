package com.machinarium.utility.common;

import java.util.logging.*;

public class ConfiguredLogger {

    public static final String FORMAT_STRING = "%2$s Message in Method(%1$s) in Class(%3$s):\n" +
            "%4$s\n\n";

    /**
     * Returns a customized {@link Logger} for displaying servlet data information.
     *
     * @param loggerName The name of the logger, created if one doesn't exist with the specified name.
     * @return The customized {@link Logger}.
     */
    public static Logger getLogger(String loggerName) {

        Logger logger = Logger.getLogger(loggerName);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {

            @Override
            public String format(LogRecord record) {
                return String.format(FORMAT_STRING,
                        record.getSourceMethodName(),
                        record.getLevel().getLocalizedName(),
                        record.getSourceClassName(),
                        record.getMessage());
            }
        });

        consoleHandler.setLevel(Level.ALL);

        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);

        return logger;
    }

    /* An instance of this class should not be created */
    private ConfiguredLogger() {}
}
