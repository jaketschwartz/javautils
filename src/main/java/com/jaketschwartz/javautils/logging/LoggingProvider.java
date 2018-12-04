package com.jaketschwartz.javautils.logging;

import java.util.function.Function;

/**
 * Globally provides logs to external classes by allowing simple Function funneling for Logger creation.
 */
public class LoggingProvider {
    // The default Logger is just a new instance of LoggerI
    private static Function<Class, ? extends Logger> loggerGenerator = LoggerI::new;

    /**
     * Sets the default Logger generation function for all external classes.  Can be extended later for external
     * frameworks, but for now it just acts as an entrypoint for subsequently created Loggers.
     * @param loggerGenerator The Function that outputs a desired Logger.
     * @param <T> The Type of class to create that implements Logger.
     */
    public static<T extends Logger> void setLoggerGenerator(final Function<Class, T> loggerGenerator) {
        LoggingProvider.loggerGenerator = loggerGenerator;
    }

    /**
     * Constructs a new instance of a Logger using the loggerGenerator Function.
     * @param loggerClass The Class to tie to the Logger.
     * @return The newly-generated Logger.
     */
    public static Logger logger(final Class loggerClass) {
        return LoggingProvider.loggerGenerator.apply(loggerClass);
    }
}
