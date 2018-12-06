package com.jaketschwartz.javautils.wrapper;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestNumeric {

    @Test
    public void testNullNumeric() {
        assertFalse("A null input value should produce an empty Numeric", Numeric.of(null).isPresent());
    }

    @Test
    public void testBadNumericType() {
        assertFalse("A bad type should produce an empty Numeric", Numeric.of(new ArrayList<>()).isPresent());
    }

    @Test
    public void testNumericConversionsWithByte() {
        final Numeric numeric = Numeric.of((byte)0x0);
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
        final Numeric numeric = Numeric.of(120);
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
        final Numeric numeric = Numeric.of((short)58);
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
        final Numeric numeric = Numeric.of((float)343.3);
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
        final Numeric numeric = Numeric.of(560L);
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
        final Numeric numeric = Numeric.of(980.5d);
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

    @Test
    public void testNumericConversionsWithBigDecimal() {
        final Numeric numeric = Numeric.of(BigDecimal.valueOf(350));
        assertNull("Our conversion to Byte should be null because 350 is too large to be a byte",
                numeric.getByte().map(Object::toString).orElse(null));
        assertEquals("Our conversion to Integer should be equal", Integer.valueOf(350),
                numeric.getInteger().orElse(null));
        assertEquals("Our conversion to Short should be equal", Short.valueOf("350"),
                numeric.getShort().orElse(null));
        assertEquals("Our conversion to Float should be equal", Float.valueOf("350"),
                numeric.getFloat().orElse(null));
        assertEquals("Our conversion to Long should be equal", Long.valueOf(350),
                numeric.getLong().orElse(null));
        assertEquals("Our conversion to Double should be equal", Double.valueOf(350),
                numeric.getDouble().orElse(null));
        assertEquals("Our conversion to BigDecimal should be equal", BigDecimal.valueOf(350),
                numeric.getBigDecimal().orElse(null));
    }

    @Test
    public void testAdd() {
        final Integer output = Numeric.of(2).add(3).getInteger().orElse(null);
        assertEquals("Two plus three equals 5", Integer.valueOf(5), output);
    }

    @Test
    public void testAddDifferentTypes() {
        final Float output = Numeric.of(10.5d).add(BigDecimal.valueOf(35)).getFloat().orElse(null);
        assertEquals("10.5 plus 35 equals 45.5", Float.valueOf("45.5"), output);
    }

    @Test
    public void testSubtract() {
        final Integer output = Numeric.of(100).subtract(30).getInteger().orElse(null);
        assertEquals("100 minus 30 equals 70", Integer.valueOf(70), output);
    }

    @Test
    public void testSubtractDifferentTypes() {
        final Double output = Numeric.of(35.7f).subtract(10).getDouble().orElse(null);
        assertEquals("35.7 minutes 10 equals 25.7", Double.valueOf(25.7d), output);
    }

    @Test
    public void testMultiply() {
        final Integer output = Numeric.of(35).multiply(5).getInteger().orElse(null);
        assertEquals("35 times 5 equals 175", Integer.valueOf(175), output);
    }

    @Test
    public void testMultiplyDifferentTypes() {
        final BigDecimal output = Numeric.of(54d).multiply(32f).getBigDecimal().orElse(null);
        assertEquals("54 times 32 equals 1728", BigDecimal.valueOf(1728).setScale(2), output.setScale(2));
    }

    @Test
    public void testDivide() {
        final Integer output = Numeric.of(150).divide(3).getInteger().orElse(null);
        assertEquals("150 divided by 3 equals 50", Integer.valueOf(50), output);
    }

    @Test
    public void testDivideDifferentTypes() {
        final Double output = Numeric.of(7).divide(2).getDouble().orElse(null);
        assertEquals("7 divided by 2 equals 3.5", Double.valueOf(3.5d), output);
    }
}
