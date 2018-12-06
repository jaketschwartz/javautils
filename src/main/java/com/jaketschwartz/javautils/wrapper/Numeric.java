package com.jaketschwartz.javautils.wrapper;

import com.jaketschwartz.javautils.logging.Logger;
import com.jaketschwartz.javautils.logging.LoggerProvider;
import com.jaketschwartz.javautils.parsing.SafeParser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * An overarching wrapper class for numerous different numeric classes in Java.  Allows seamless conversions between
 * each other, where possible; attempts to be as similar to typecasting as possible.
 * TODO: Home-grow our own mathematical operations instead of cheating by wrapping BigDecimal operations.
 */
public final class Numeric {
    private static final Logger log = LoggerProvider.logger(Numeric.class);
    // Current registry of supported numeric classes
    private static final List<Class> NUMERIC_CLASSES = Arrays.asList(Byte.class, Integer.class, Short.class,
            Float.class, Long.class, Double.class, BigDecimal.class);
    private static final String NUMERIC_CLASSES_MESSAGE =
            NUMERIC_CLASSES.stream().map(Class::getSimpleName).collect(Collectors.joining(","));
    private Optional<BigDecimal> value = Optional.empty();

    /**
     * Statically creates an instance of a Numeric object for the provided value.
     * @param value Any object that conforms to the current NUMERIC_CLASSES List.
     * @param <T> Any Type of object that hopefully conforms to the current NUMERIC_CLASSES List.
     * @return The Numeric-wrapped value.
     */
    public static<T> Numeric of(final T value) {
        return new Numeric(value);
    }

    /**
     * Adds the input value to the value of the current Numeric and returns a new Numeric, maintaining immutability.
     * @param augend The value to add to the current value.
     * @param <T> Any Type that can be converted to a Numeric.
     * @return A new Numeric representing the mathematical transformation.
     */
    public<T> Numeric add(final T augend) {
        return this.applyMath(augend, BigDecimal::add);
    }

    /**
     * Subtracts the input value from the value of the current Numeric and returns a new Numeric, maintaining
     * immutability.
     * @param subtrahend The value to subtract from the current value.
     * @param <T> Any Type that can be converted to a Numeric.
     * @return A new Numeric representing the mathematical transformation.
     */
    public<T> Numeric subtract(final T subtrahend) {
        return this.applyMath(subtrahend, BigDecimal::subtract);
    }

    /**
     * Multiplies the input value by the value of the current Numeric and returns a new Numeric, maintaining
     * immutability.
     * @param multiplicand The value to multiply the current value by.
     * @param <T> Any Type that can be converted to a Numeric.
     * @return A new Numeric representing the mathematical transformation.
     */
    public<T> Numeric multiply(final T multiplicand) {
        return this.applyMath(multiplicand, BigDecimal::multiply);
    }

    /**
     * Divides the value of the current Numeric by the input value and returns a new Numeric, maintaining immutability.
     * @param divisor The value to divide the current Numeric value by.
     * @param <T> Any Type that can be converted to a Numeric.
     * @return A new Numeric representing the mathematical transformation.
     */
    public<T> Numeric divide(final T divisor) {
        return this.applyMath(divisor, BigDecimal::divide);
    }

    /**
     * Attempts to safely convert the internalized value to a Byte.
     * @return The Byte, or an empty Optional on errors.
     */
    public Optional<Byte> getByte() {
        return value.map(BigDecimal::toString).flatMap(SafeParser::byteFromString);
    }

    /**
     * Attempts to safely convert the internalized value to an Integer.
     * @return The Integer, or an empty Optional on errors.
     */
    public Optional<Integer> getInteger() {
        return value.map(BigDecimal::toString).flatMap(SafeParser::integerFromString);
    }

    /**
     * Attempts to safely convert the internalized value to a Short.
     * @return The Short, or an empty Optional on errors.
     */
    public Optional<Short> getShort() {
        return value.map(BigDecimal::toString).flatMap(SafeParser::shortFromString);
    }

    /**
     * Attempts to safely convert the internalized value to a Float.
     * @return The Float, or an empty Optional on errors.
     */
    public Optional<Float> getFloat() {
        return value.map(BigDecimal::toString).flatMap(SafeParser::floatFromString);
    }

    /**
     * Attempts to safely convert the internalized value to a Long.
     * @return The Long, or an empty Optional on errors.
     */
    public Optional<Long> getLong() {
        return value.map(BigDecimal::toString).flatMap(SafeParser::longFromString);
    }

    /**
     * Attempts to safely convert the internalized value to a Double.
     * @return The Double, or an empty Optional on errors.
     */
    public Optional<Double> getDouble() {
        return value.map(BigDecimal::toString).flatMap(SafeParser::doubleFromString);
    }

    /**
     * Attempts to safely convert the internalized value to a BigDecimal.
     * @return The BigDecimal, or an empty Optional on errors.
     */
    public Optional<BigDecimal> getBigDecimal() {
        return value;
    }

    /**
     * Determines if the internal BigDecimal value exists.
     * @return True IF the Optionally-wrapped BigDecimal is present.
     */
    public boolean isPresent() {
        return value.isPresent();
    }

    /**
     * Constructs a new Numeric object with regards for null values and an input whitelist.
     * @param value The value to attempt to convert to a Numeric.
     * @param <T> Any object type, but hopefully something on the whitelist.
     */
    private<T> Numeric(final T value) {
        if (value == null) {
            log.warn("Null value supplied to Numeric! Please enter any of: [{}]", NUMERIC_CLASSES_MESSAGE);
            return;
        }
        // If an unsupported class is provided, list the potential classes and throw an IAE
        if (NUMERIC_CLASSES.stream().noneMatch(clazz -> clazz.isInstance(value))) {
            log.error("Numeric does not support instances of {}! Please enter any of: [{}]",
                    value.getClass().getSimpleName(), NUMERIC_CLASSES_MESSAGE);
            return;
        }
        this.value = SafeParser.bigDecimalFromString(value.toString());
    }

    /**
     * Transforms the input into a Numeric, attempts to apply a math function to its internal value in relation to the
     * currently-encapsulated value for this, and ships the completed computation out as a new Numeric, maintaining
     * immutability.
     * @param manipulation The input value to transform into a Numeric.
     * @param manipulator The Function describing how to transform the value of this.
     * @param <T> Any Type that can be converted to a Numeric from the whitelist.
     * @return A new Numeric representing the mathematical transformation.
     */
    private<T> Numeric applyMath(final T manipulation,
                                 final BiFunction<BigDecimal, BigDecimal, BigDecimal> manipulator) {
        if (manipulator == null) {
            log.error("This is a bug! An internal call to applyMath supplied a null manipulation function!");
            return this;
        }
        if (!this.value.isPresent()) {
            return this;
        }
        final BigDecimal input = new Numeric(manipulation).getBigDecimal().orElse(null);
        if (input == null) {
            return this;
        }
        return new Numeric(manipulator.apply(this.value.get(), input));
    }
}
