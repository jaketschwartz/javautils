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
}
