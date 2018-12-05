package com.jaketschwartz.javautils.wrapper;

import com.jaketschwartz.javautils.logging.Logger;
import com.jaketschwartz.javautils.logging.LoggerProvider;
import com.jaketschwartz.javautils.parsing.SafeParser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An overarching wrapper class for numerous different numeric classes in Java.  Allows seamless conversions between
 * each other, where possible; attempts to be as similar to typecasting as possible.
 * @param <T>
 */
public class Numeric<T> {
    private static final Logger log = LoggerProvider.logger(Numeric.class);
    // Current registry of supported numeric classes
    private static final List<Class> NUMERIC_CLASSES = Arrays.asList(Byte.class, Integer.class, Short.class,
            Float.class, Long.class, Double.class);
    private T value;

    private Numeric(T value) {
        // If a null value is supplied, NPE immediately
        Objects.requireNonNull(value, "Numeric requres a non-null value!");
        // If an unsupported class is provided, list the potential classes and throw an IAE
        if (NUMERIC_CLASSES.stream().noneMatch(clazz -> clazz.isInstance(value))) {
            final String supportedClasses =
                    NUMERIC_CLASSES.stream().map(Class::getSimpleName).collect(Collectors.joining(","));
            throw new IllegalArgumentException(String.format("Numeric does not support instances of [%s]! Please " +
                    "enter any of: [%s]", value.getClass().getSimpleName(), supportedClasses));
        }
        this.value = value;
    }

    /**
     * Statically creates an instance of a Numeric object for the provided value.
     * @param value Any object that conforms to the current NUMERIC_CLASSES List.
     * @param <T> Any Type of object that hopefully conforms to the current NUMERIC_CLASSES List.
     * @return The Numeric-wrapped value.
     */
    public static<T> Numeric<T> of(final T value) {
        return new Numeric<>(value);
    }

    /**
     * Attempts to create an instance of Numeric, isolating any potential exceptions within.
     * @param value The value to convert to a Numeric.
     * @param <T> Any Type of object that hopefully conforms to the current NUMERIC_CLASSES List.
     * @return The Numeric-wrapped value.
     */
    public static<T> Optional<Numeric<T>> parse(final T value) {
        try {
            return Optional.of(new Numeric<>(value));
        } catch(NullPointerException e) {
            log.error("Failed to create a Numeric from a null value", e);
        } catch (IllegalArgumentException e) {
            log.error("Bad input provided to Numeric", e);
        }
        return Optional.empty();
    }

    /**
     * Attempts to safely convert the internalized value to a Byte.
     * @return The Byte, or an empty Optional on errors.
     */
    public Optional<Byte> getByte() {
        return SafeParser.byteFromString(value.toString());
    }

    /**
     * Attempts to safely convert the internalized value to an Integer.
     * @return The Integer, or an empty Optional on errors.
     */
    public Optional<Integer> getInteger() {
        return SafeParser.integerFromString(value.toString());
    }

    /**
     * Attempts to safely convert the internalized value to a Short.
     * @return The Short, or an empty Optional on errors.
     */
    public Optional<Short> getShort() {
        return SafeParser.shortFromString(value.toString());
    }

    /**
     * Attempts to safely convert the internalized value to a Float.
     * @return The Float, or an empty Optional on errors.
     */
    public Optional<Float> getFloat() {
        return SafeParser.floatFromString(value.toString());
    }

    /**
     * Attempts to safely convert the internalized value to a Long.
     * @return The Long, or an empty Optional on errors.
     */
    public Optional<Long> getLong() {
        return SafeParser.longFromString(value.toString());
    }

    /**
     * Attempts to safely convert the internalized value to a Double.
     * @return The Double, or an empty Optional on errors.
     */
    public Optional<Double> getDouble() {
        return SafeParser.doubleFromString(value.toString());
    }

    /**
     * Attempts to safely convert the internalized value to a BigDecimal.
     * @return The BigDecimal, or an empty Optional on errors.
     */
    public Optional<BigDecimal> getBigDecimal() {
        return SafeParser.bigDecimalFromString(value.toString());
    }

    /**
     * Fetches the internalized value in its initial incarnation.
     * @return The original value that created this Numeric.
     */
    public T get() {
        return value;
    }
}
