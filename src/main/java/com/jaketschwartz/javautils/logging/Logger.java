package com.jaketschwartz.javautils.logging;

/**
 * A top-level interface that defines how a logging class should behave.  Used to allow extensibility in external
 * applications utilizing these utilities.
 */
public interface Logger {
    /**
     * Displays a message at the TRACE level.
     * @param text The text to display.
     * @param args Any arguments to format into the text.
     */
    void trace(String text, Object... args);

    /**
     * Displays a message at the DEBUG level.
     * @param text The text to display.
     * @param args Any arguments to format into the text.
     */
    void debug(String text, Object... args);

    /**
     * Displays a message at the INFO level.
     * @param text The text to display.
     * @param args Any arguments to format into the text.
     */
    void info(String text, Object... args);

    /**
     * Displays a message at the WARN level.
     * @param text The text to display.
     * @param args Any arguments to format into the text.
     */
    void warn(String text, Object... args);

    /**
     * Displays a message at the ERROR level.
     * @param text The text to display.
     * @param args Any arguments to format into the text.
     */
    void error(String text, Object... args);
}
