package com.jaketschwartz.javautils.logging;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * A very basic implementation of the com.jaketschwartz.javautils.logging.Logger interface.  This is used as the default
 * logger when a logging framework is not statically injected into the utils classes.  Not even close to as verbose as
 * the numerous popular logging solutions available, so it's recommended that
 */
public class LoggerI implements Logger {
    private static final String REPLACEMENT_SYMBOL = "{}";
    private static final Integer CLASS_NAME_DISPLAY_SIZE = 30;
    private Class loggingForClass;
    // Default to TRACE logs
    private LogLevel logLevel = LogLevel.TRACE;

    public LoggerI(final Class loggingForClass) {
        Objects.requireNonNull(loggingForClass, "You must provide a non-null class to create a LoggerI!");
        this.loggingForClass = loggingForClass;
    }

    public LoggerI(final Class loggingForClass, final LogLevel logLevel) {
        Objects.requireNonNull(loggingForClass, "You must provide a non-null class to create a LoggerI!");
        Objects.requireNonNull(logLevel, "You must provide a non-null LogLevel to create a LoggerI!");
        this.loggingForClass = loggingForClass;
        this.logLevel = logLevel;
    }

    public void setLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public void trace(final String text, final Object... args) {
        this.displayLog(LogLevel.TRACE, text, args);
    }

    @Override
    public void debug(final String text, final Object... args) {
        this.displayLog(LogLevel.DEBUG, text, args);
    }

    @Override
    public void info(final String text, final Object... args) {
        this.displayLog(LogLevel.INFO, text, args);
    }

    @Override
    public void warn(final String text, final Object... args) {
        this.displayLog(LogLevel.WARN, text, args);
    }

    @Override
    public void error(final String text, final Object... args) {
        this.displayLog(LogLevel.ERROR, text, args);
    }

    /**
     * An overarching set of functionality for displaying a log. Replaces all instances of '{}' in the text object with
     * the corresponding args, and spits out a stack trace at the end if the last element is a Throwable type.
     * @param currentLevel The LogLevel to display. Checked vs internally-established log level weight.
     * @param message The text to format.
     * @param args The arguments to format into the text.
     */
    private void displayLog(final LogLevel currentLevel, String message, Object... args) {
        if (currentLevel == null) {
            throw new RuntimeException("This should never happen! An internal LogLevel was supplied as null!");
        }
        // We display logs on or above our log level.  Lowest = trace | Highest = error.  If our log level is ERROR,
        // and we attempt to log a WARN, we will not display the WARN message because's it's below our log level.  If we
        // specify INFO level, we will display INFO, WARN, and ERROR logs, but not DEBUG and TRACE.
        if (currentLevel.weight() < this.logLevel.weight()) {
            return;
        }
        final StringBuilder logBuilder = new StringBuilder();
        // Ex: [ INFO][com.whatever.otherpac…YourClass] -
        logBuilder.append("[")
                  .append(String.format("%5.5s", currentLevel.name()))
                  .append("]")
                  .append("[")
                  .append(LocalDateTime.now())
                  .append("]")
                  .append("[")
                  .append(String.format("%" + CLASS_NAME_DISPLAY_SIZE + "." + CLASS_NAME_DISPLAY_SIZE + "s",
                                        this.formatClassDisplay()))
                  .append("]")
                  .append(" - ");
        // If we have no text, just notify the user
        if (message == null) {
            System.out.println(logBuilder.append("null text supplied to logger").toString());
            return;
        }
        // If we have no args, we can just spit out the text
        if (args == null || args.length == 0) {
            System.out.println(logBuilder.append(message).toString());
            return;
        }
        // If our last argument is an Exception, we can mimic Log4j and append the value as a stack trace
        String exceptionText = null;
        if (args[args.length - 1] instanceof Throwable) {
            exceptionText = this.formatStackTrace((Throwable)args[args.length - 1]);
            // Slice off the last index of the array to prevent it from being parsed later on
            args = Arrays.copyOf(args, args.length - 1);
        }
        // Iterate on all object arguments, null check them, and then replace instances of {} with their stringified
        // versions
        for (final Object object: args) {
            // Although this makes linear evaluations with perfect input more expensive, in instances of larger
            // input than amounts of replacement characters, this will break FAR before the expense becomes relevant
            if (!message.contains(REPLACEMENT_SYMBOL)) {
                break;
            }
            message = message.replaceFirst(String.format("\\%s", REPLACEMENT_SYMBOL),
                                           Optional.ofNullable(object)
                                                   .map(Object::toString)
                                                   .map(Matcher::quoteReplacement)
                                                   .orElse("null"));
        }
        logBuilder.append(message);
        // If we had an exception, we can append our exception text on a new line
        if (exceptionText != null) {
            logBuilder.append("\n").append(exceptionText);
        }
        System.out.println(logBuilder.toString());
    }

    /**
     * Displays the encapsulated class for the logger by showing the canonical name, collapsed so that the class name
     * is always visible at the end, if possible.
     * @return
     */
    private String formatClassDisplay() {
        final String canonicalName = loggingForClass.getCanonicalName();
        // If the full canonical name fits, we can just send that back and make the user experience better
        if (canonicalName.length() <= CLASS_NAME_DISPLAY_SIZE) {
            return loggingForClass.getCanonicalName();
        }
        final String simpleName = loggingForClass.getSimpleName();
        // If the simple name is a perfect fit, we can just send it back
        if (simpleName.length() == CLASS_NAME_DISPLAY_SIZE) {
            return simpleName;
        }
        // If the simple name is greater than the max length, we should replace the last character with ellipses
        if (simpleName.length() > CLASS_NAME_DISPLAY_SIZE) {
            return simpleName.substring(0, CLASS_NAME_DISPLAY_SIZE - 1) + "…";
        }
        // Fetch the prefix of the canonical name to prevent weirdness with concatenating the package prefix to the
        // simple name
        final String canonicalPrefix = canonicalName.substring(0, canonicalName.lastIndexOf("."));
        // Collapse the canonical name and simple name to create a displayable format
        return String.format("%s…%s",
                             canonicalPrefix.substring(0, CLASS_NAME_DISPLAY_SIZE - simpleName.length() - 1),
                             simpleName);
    }

    /**
     * Ensure a stack trace has all viable elements, and, if so, format it to the appropriate display.
     * @param throwable The thrown exception.
     * @return The appropriately-formatted stack-trace, or null if bad input was given.
     */
    private String formatStackTrace(final Throwable throwable) {
        if (throwable == null || throwable.getStackTrace() == null || throwable.getStackTrace().length == 0) {
            return null;
        }
        final StringBuilder stackTraceBuilder = new StringBuilder();
        stackTraceBuilder.append(throwable.getMessage()).append("\n");
        for (final StackTraceElement element: throwable.getStackTrace()) {
            stackTraceBuilder.append("\tat ")
                             .append(element.getClassName())
                             .append(" ")
                             .append(element.getMethodName())
                             .append("():")
                             .append(element.getLineNumber())
                             .append("\n");
        }
        return stackTraceBuilder.toString();
    }
}
