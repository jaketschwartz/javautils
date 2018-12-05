package com.jaketschwartz.javautils.parsing;

import com.jaketschwartz.javautils.logging.LogLevel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestSafeParser {
    @Test
    public void testEnumValueNullInput() {
        assertFalse("A null input value should produce an empty Optional",
                    SafeParser.enumValue(null, LogLevel.class).isPresent());
    }

    @Test
    public void testEnumValueNullClass() {
        assertFalse("A null target class should produce an empty Optional",
                    SafeParser.enumValue("ANYTHING", null).isPresent());
    }

    @Test
    public void testEnumValueBadInputString() {
        assertFalse("An incompatible string should produce an empty Optional",
                    SafeParser.enumValue("BAD_LEVEL", LogLevel.class).isPresent());
    }

    @Test
    public void testEnumValueGoodInputString() {
        assertEquals("A compatible input string should produce the desired conversion",
                     LogLevel.INFO, SafeParser.enumValue("INFO", LogLevel.class).get());
    }

    @Test
    public void testByteFromStringBadInput() {
        assertFalse("A null input should produce an empty Optional",
                SafeParser.byteFromString(null).isPresent());
        assertFalse("Bad characters should produce an empty Optional",
                SafeParser.byteFromString("ASFKJFD(D)SF#NNF$NF$").isPresent());
        assertFalse("Too high a number should produce an empty Optional",
                SafeParser.byteFromString("1343").isPresent());
    }

    @Test
    public void testByteFromStringGoodInput() {
        assertEquals("We should receive a zero byte", Byte.valueOf((byte)0x0),
                SafeParser.byteFromString("0").orElse(null));
    }

    @Test
    public void testShortFromStringBadInput() {
        assertFalse("A null input should produce an empty Optional",
                SafeParser.shortFromString(null).isPresent());
        assertFalse("Bad characters should produce an empty Optional",
                SafeParser.shortFromString("ASFKJFD(D)SF#NNF$NF$").isPresent());
        assertFalse("Too high a number should produce an empty Optional",
                SafeParser.shortFromString("40000").isPresent());
    }

    @Test
    public void testShortFromStringGoodInput() {
        assertEquals("We should receive a Short value of 30,000", Short.valueOf("30000"),
                SafeParser.shortFromString("30000").orElse(null));
    }

    @Test
    public void testIntegerFromStringBadInput() {
        assertFalse("A null input should produce an empty Optional",
                SafeParser.integerFromString(null).isPresent());
        assertFalse("Bad characters should produce an empty Optional",
                SafeParser.integerFromString("ASFKJFD(D)SF#NNF$NF$").isPresent());
        assertFalse("Too high a number should produce an empty Optional",
                SafeParser.integerFromString("2147483648").isPresent());
    }

    @Test
    public void testIntegerFromStringGoodInput() {
        assertEquals("We should receive an Integer value of 2,147,483,647", Integer.valueOf(2147483647),
                SafeParser.integerFromString("2147483647").orElse(null));
    }

    @Test
    public void testFloatFromStringBadInput() {
        assertFalse("A null input should produce an empty Optional",
                SafeParser.floatFromString(null).isPresent());
        assertFalse("Bad characters should produce an empty Optional",
                SafeParser.floatFromString("ASFKJFD(D)SF#NNF$NF$").isPresent());
    }

    @Test
    public void testFloatFromStringGoodInput() {
        assertEquals("We should receive a Float value of MAX", Float.valueOf(Float.MAX_VALUE),
                SafeParser.floatFromString(Float.valueOf(Float.MAX_VALUE).toString()).orElse(null));
    }

    @Test
    public void testLongFromStringBadInput() {
        assertFalse("A null input should produce an empty Optional",
                SafeParser.longFromString(null).isPresent());
        assertFalse("Bad characters should produce an empty Optional",
                SafeParser.longFromString("ASFKJFD(D)SF#NNF$NF$").isPresent());
    }

    @Test
    public void testLongFromStringGoodInput() {
        assertEquals("We should receive a Long value of MAX", Long.valueOf(Long.MAX_VALUE),
                SafeParser.longFromString(Long.valueOf(Long.MAX_VALUE).toString()).orElse(null));
    }

    @Test
    public void testDoubleFromStringBadInput() {
        assertFalse("A null input should produce an empty Optional",
                SafeParser.doubleFromString(null).isPresent());
        assertFalse("Bad characters should produce an empty Optional",
                SafeParser.doubleFromString("ASFKJFD(D)SF#NNF$NF$").isPresent());
    }

    @Test
    public void testDoubleFromStringGoodInput() {
        assertEquals("We should receive a Double value of MAX", Double.valueOf(Double.MAX_VALUE),
                SafeParser.doubleFromString(Double.valueOf(Double.MAX_VALUE).toString()).orElse(null));
    }
}
