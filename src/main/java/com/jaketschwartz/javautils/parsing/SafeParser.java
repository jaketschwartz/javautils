package com.jaketschwartz.javautils.parsing;

import com.jaketschwartz.javautils.logging.Logger;
import com.jaketschwartz.javautils.logging.LoggerProvider;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class safely parses values from one type to another, giving empty Optional output when bad input is encountered.
 */
public class SafeParser {
    private static final Logger log = LoggerProvider.logger(SafeParser.class);

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

    /**
     * Converts a String to a Byte.
     * @param string The string value to attempt to convert.
     * @return The Byte, or an empty Optional on bad input or errors.
     */
    public static Optional<Byte> byteFromString(final String string) {
        return SafeParser.fromString(Byte.class, wrapRemoveDecimals(Byte::valueOf), "byteFromString()", string);
    }

    /**
     * Converts a String to a Short.
     * @param string The string value to attempt to convert.
     * @return The Short, or an empty Optional on bad input or errors.
     */
    public static Optional<Short> shortFromString(final String string) {
        return SafeParser.fromString(Short.class, wrapRemoveDecimals(Short::valueOf), "shortFromString()", string);
    }

    /**
     * Converts a String to an Integer.
     * @param string The string value to attempt to convert.
     * @return The Integer, or an empty Optional on bad input or errors.
     */
    public static Optional<Integer> integerFromString(final String string) {
        return SafeParser.fromString(Integer.class, wrapRemoveDecimals(Integer::valueOf), "integerFromString()", string);
    }

    /**
     * Converts a String to a Float.
     * @param string The string value to attempt to convert.
     * @return The Float, or an empty Optional on bad input or errors.
     */
    public static Optional<Float> floatFromString(final String string) {
        return SafeParser.fromString(Float.class, Float::valueOf, "floatFromString()", string);
    }

    /**
     * Converts a String to a Long.
     * @param string The string value to attempt to convert.
     * @return The Long, or an empty Optional on bad input or errors.
     */
    public static Optional<Long> longFromString(final String string) {
        return SafeParser.fromString(Long.class, wrapRemoveDecimals(Long::valueOf), "longFromString()", string);
    }

    /**
     * Converts a String to a Double.
     * @param string The string value to attempt to convert.
     * @return The Double, or an empty Optional on bad input or errors.
     */
    public static Optional<Double> doubleFromString(final String string) {
        return SafeParser.fromString(Double.class, Double::valueOf, "doubleFromString()", string);
    }

    /**
     * Converts a String to a BigDecimal.
     * @param string The string value to attempt to convert.
     * @return The BigDecimal, or an empty Optional on bad input or errors.
     */
    public static Optional<BigDecimal> bigDecimalFromString(final String string) {
        return SafeParser.fromString(BigDecimal.class, BigDecimal::new, "bigDecimalFromString()", string);
    }

    /**
     * Dynamically converts a String to a requested Object type by use of an input Function.
     * @param objectType The Class of the object to convert to. Only used for logging.
     * @param converter The Function that converts the String to the specific Type.
     * @param methodName The method being used for this conversion. Only used for logging.
     * @param string The String to be converted.
     * @param <T> Any Type of object that can be parsed from a String.
     * @return The successfully-converted Object, or an empty Optional on exceptions or bad input.
     */
    private static<T> Optional<T> fromString(final Class<T> objectType,
                                             final Function<String, T> converter,
                                             final String methodName,
                                             String string) {
        if (objectType == null || converter == null) {
            log.error("The should never happen! The internal functionality is broken for {}! We received a " +
                    "null type or converter function internally to convert [{}]", methodName, string);
            return Optional.empty();
        }
        if (string == null) {
            log.warn("Null string provided to {}!", methodName);
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(converter.apply(string));
        } catch (Exception e) {
            log.error("Failed to convert a String[{}] to a/an [{}] using {}!", string, objectType.getSimpleName(),
                    methodName, e);
            return Optional.empty();
        }
    }

    /**
     * Generates a function replica of the initial function, but shaves off all decimals from the initial string input.
     * Assumes a non-null String input to the function.
     * @param converter The Function to convert a String to a T.
     * @param <T> Any Type of object that can be parsed from a String.
     * @return The wrapped Function, or null if the originally-supplied function was null.
     */
    private static<T> Function<String, T> wrapRemoveDecimals(final Function<String, T> converter) {
        if (converter == null) {
            log.error("wrapRemoveDecimals should NOT receive null functions!");
            return null;
        }
        return string -> converter.apply(string.split("\\.")[0]);
    }
}
