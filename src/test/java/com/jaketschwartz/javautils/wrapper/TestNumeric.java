package com.jaketschwartz.javautils.wrapper;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestNumeric {

    @Test(expected = NullPointerException.class)
    public void testNullNumeric() {
        Numeric.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadNumericType() {
        Numeric.of(new ArrayList<>());
    }

    @Test
    public void testNullNumericParse() {
        assertFalse("We should not get a Numeric back on null input", Numeric.parse(null).isPresent());
    }

    @Test
    public void testBadNumericTypeParse() {
        assertFalse("We should not get a Numeric back on bad input",
                // BigDecimal support is being mused as a potential necessity, so this test eventually failing will be
                // a good reminder at some point of the original intentions
                Numeric.parse(BigDecimal.valueOf(10)).isPresent());
    }

    @Test
    public void testNumericConversionsWithByte() {
        final Numeric<Byte> numeric = Numeric.of((byte)0x0);
        assertEquals("Our conversion to Byte should be equal", Byte.toString((byte)0x0),
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(0),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("0"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("0"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(0L),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(0d),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(0),
                numeric.getBigDecimal().orElse(null));
    }

    @Test
    public void testNumericConversionsWithInteger() {
        final Numeric<Integer> numeric = Numeric.of(120);
        assertEquals("Our conversion to Byte should be equal", "120",
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(120),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("120"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("120"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(120L),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(120d),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(120),
                numeric.getBigDecimal().orElse(null));
    }

    @Test
    public void testNumericConversionsWithShort() {
        final Numeric<Short> numeric = Numeric.of((short)58);
        assertEquals("Our conversion to Byte should be equal", "58",
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(58),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("58"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("58"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(58L),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(58d),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(58),
                numeric.getBigDecimal().orElse(null));
    }

    @Test
    public void testNumericConversionsWithFloat() {
        final Numeric<Float> numeric = Numeric.of((float)343.3);
        assertNull("Our conversion to Byte should be null because 343.3 is too large to be a byte",
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(343),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("343"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("343.3"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(343L),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(343.3d),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(343.3),
                numeric.getBigDecimal().orElse(null));
    }

    @Test
    public void testNumericConversionsWithLong() {
        final Numeric<Long> numeric = Numeric.of(560L);
        assertNull("Our conversion to Byte should be null because 560 is too large to be a byte",
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(560),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("560"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("560"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(560),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(560d),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(560),
                numeric.getBigDecimal().orElse(null));
    }

    @Test
    public void testNumericConversionsWithDouble() {
        final Numeric<Double> numeric = Numeric.of(980.5d);
        assertNull("Our conversion to Byte should be null because 980.5 is too large to be a byte",
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(980),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("980"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("980.5"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(980),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(980.5d),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(980.5d),
                numeric.getBigDecimal().orElse(null));
    }
}
