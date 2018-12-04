package com.jaketschwartz.javautils.parsing;

import com.jaketschwartz.javautils.logging.LogLevel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class TestSafeParser {
    @Test
    public void testEnumValueNullInput() {
        assertFalse("A null input value should produce an empty Optional",
                    SafeParser.enumValue(null, LogLevel.class).isPresent());
    }
}
