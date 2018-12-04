package com.jaketschwartz.javautils.parsing;

import com.jaketschwartz.javautils.logging.Logger;
import com.jaketschwartz.javautils.logging.LoggingProvider;

import java.util.Optional;

/**
 * This class safely parses values from one type to another, giving empty Optional output when bad input is encountered.
 */
public class SafeParser {
    private static final Logger log = LoggingProvider.logger(SafeParser.class);

    /**
     * Converts a String to its Enum value counterpart.
     * @param value The String to convert to an Enum.
     * @param enumClass The Enum type to convert to.
     * @param <T> Any Enum Type.
     * @return The successfully-parsed Enum value in the class of choice, or an empty Optional on null or bad input.
     */
    public static<T extends Enum<T>> Optional<T> enumValue(final String value, final Class<T> enumClass) {
        if (value == null || enumClass == null) {
            log.warn("Null value [{}] or class [{}] provided to valueOf()", value, enumClass);
            return Optional.empty();
        }
        try {
            return Optional.of(Enum.valueOf(enumClass, value));
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert [{}] to a/an [{}]", value, enumClass.getSimpleName(), e);
            return Optional.empty();
        }
    }
}
