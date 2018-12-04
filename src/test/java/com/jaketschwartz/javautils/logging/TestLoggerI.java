package com.jaketschwartz.javautils.logging;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLoggerI {
    private static final String EMPTY_STRING = "";
    private static ByteArrayOutputStream capturedOut;
    private static final PrintStream originalOut = System.out;

    @Before
    public void beforeEach() {
        // Reset the output prior to each test to ensure a clean test environment
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterClass
    public static void cleanup() {
        // Re-attach System.out to the System's output
        System.setOut(originalOut);
    }

    @Test(expected = NullPointerException.class)
    public void testNullLoggingClassInput() {
        new LoggerI(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullLogLevelInput() {
        new LoggerI(TestLoggerI.class, null);
    }

    @Test
    public void testTraceTooHighLevel() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).trace("HELLO");
        assertEquals("We should have no TRACE output from an ERROR level logger",
                     EMPTY_STRING,
                     capturedOut.toString());
        new LoggerI(TestLoggerI.class, LogLevel.WARN).trace("HI THERE");
        assertEquals("We should have no TRACE output from a WARN level logger",
                     EMPTY_STRING,
                     capturedOut.toString());
        new LoggerI(TestLoggerI.class, LogLevel.INFO).trace("PLEASE HEAR MY VOICE");
        assertEquals("We should have no TRACE output from an INFO level logger",
                     EMPTY_STRING,
                     capturedOut.toString());
        new LoggerI(TestLoggerI.class, LogLevel.DEBUG).trace("OMG WHY CAN'T YOU HEAR ME");
        assertEquals("We should have no TRACE output from a DEBUG level logger",
                     EMPTY_STRING,
                     capturedOut.toString());
    }

    @Test
    public void testTraceNullText() {
        new LoggerI(TestLoggerI.class, LogLevel.TRACE).trace(null);
        assertOutputMatches("We should receive a message about null input",
                "TRACE", "null text supplied to logger");
    }

    @Test
    public void testTraceNoAdditionalArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.TRACE).trace("HELLO");
        assertOutputMatches("We should receive our HELLO message", "TRACE", "HELLO");
    }

    @Test
    public void testTraceWithArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.TRACE).trace("HI {} {}", "A", "B");
        assertOutputMatches("We should receive our HI message", "TRACE", "HI A B");
    }

    @Test
    public void testTraceWithExceptionAndArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.TRACE).trace("HELLO {}", 10, new Exception("BAD STUFF"));
        assertOutputMatches("We should have gotten our HELLO message with a stack trace", "TRACE",
                "HELLO 10\nBAD STUFF\n\tat com.jaketschwartz");
    }

    @Test
    public void testDebugTooHighLevel() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).debug("HELLO");
        assertEquals("We should have no DEBUG output from an ERROR level logger",
                EMPTY_STRING,
                capturedOut.toString());
        new LoggerI(TestLoggerI.class, LogLevel.WARN).debug("HI THERE");
        assertEquals("We should have no DEBUG output from a WARN level logger",
                EMPTY_STRING,
                capturedOut.toString());
        new LoggerI(TestLoggerI.class, LogLevel.INFO).debug("PLEASE HEAR MY VOICE");
        assertEquals("We should have no DEBUG output from an INFO level logger",
                EMPTY_STRING,
                capturedOut.toString());
    }

    @Test
    public void testDebugNullText() {
        new LoggerI(TestLoggerI.class, LogLevel.DEBUG).debug(null);
        assertOutputMatches("We should receive a message about null input",
                "DEBUG", "null text supplied to logger");
    }

    @Test
    public void testDebugNoAdditionalArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.DEBUG).debug("HELLO");
        assertOutputMatches("We should receive our HELLO message", "DEBUG", "HELLO");
    }

    @Test
    public void testDebugWithArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.DEBUG).debug("HI {} {}", "A", "B");
        assertOutputMatches("We should receive our HI message", "DEBUG", "HI A B");
    }

    @Test
    public void testDebugWithExceptionAndArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.DEBUG).debug("HELLO {}", 10, new Exception("BAD STUFF"));
        assertOutputMatches("We should have gotten our HELLO message with a stack trace", "DEBUG",
                "HELLO 10\nBAD STUFF\n\tat com.jaketschwartz");
    }

    @Test
    public void testInfoTooHighLevel() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).info("HELLO");
        assertEquals("We should have no INFO output from an ERROR level logger",
                EMPTY_STRING,
                capturedOut.toString());
        new LoggerI(TestLoggerI.class, LogLevel.WARN).info("HI THERE");
        assertEquals("We should have no INFO output from a WARN level logger",
                EMPTY_STRING,
                capturedOut.toString());
    }

    @Test
    public void testInfoNullText() {
        new LoggerI(TestLoggerI.class, LogLevel.INFO).info(null);
        assertOutputMatches("We should receive a message about null input",
                "INFO", "null text supplied to logger");
    }

    @Test
    public void testInfoNoAdditionalArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.INFO).info("HELLO");
        assertOutputMatches("We should receive our HELLO message", "INFO", "HELLO");
    }

    @Test
    public void testInfoWithArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.INFO).info("HI {} {}", "A", "B");
        assertOutputMatches("We should receive our HI message", "INFO", "HI A B");
    }

    @Test
    public void testInfoWithExceptionAndArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.INFO).info("HELLO {}", 10, new Exception("BAD STUFF"));
        assertOutputMatches("We should have gotten our HELLO message with a stack trace", "INFO",
                "HELLO 10\nBAD STUFF\n\tat com.jaketschwartz");
    }

    @Test
    public void testWarnTooHighLevel() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).warn("HELLO");
        assertEquals("We should have no WARN output from an ERROR level logger",
                EMPTY_STRING,
                capturedOut.toString());
    }

    @Test
    public void testWarnNullText() {
        new LoggerI(TestLoggerI.class, LogLevel.WARN).warn(null);
        assertOutputMatches("We should receive a message about null input",
                "WARN", "null text supplied to logger");
    }

    @Test
    public void testWarnNoAdditionalArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.WARN).warn("HELLO");
        assertOutputMatches("We should receive our HELLO message", "WARN", "HELLO");
    }

    @Test
    public void testWarnWithArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.WARN).warn("HI {} {}", "A", "B");
        assertOutputMatches("We should receive our HI message", "WARN", "HI A B");
    }

    @Test
    public void testWarnWithExceptionAndArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.WARN).warn("HELLO {}", 10, new Exception("BAD STUFF"));
        assertOutputMatches("We should have gotten our HELLO message with a stack trace", "WARN",
                "HELLO 10\nBAD STUFF\n\tat com.jaketschwartz");
    }

    @Test
    public void testErrorNullText() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).error(null);
        assertOutputMatches("We should receive a message about null input",
                "ERROR", "null text supplied to logger");
    }

    @Test
    public void testErrorNoAdditionalArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).error("HELLO");
        assertOutputMatches("We should receive our HELLO message", "ERROR", "HELLO");
    }

    @Test
    public void testErrorWithArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.ERROR).error("HI {} {}", "A", "B");
        assertOutputMatches("We should receive our HI message", "ERROR", "HI A B");
    }

    @Test
    public void testErrorWithExceptionAndArgs() {
        new LoggerI(TestLoggerI.class, LogLevel.WARN).error("HELLO {}", 10, new Exception("BAD STUFF"));
        assertOutputMatches("We should have gotten our HELLO message with a stack trace", "ERROR",
                "HELLO 10\nBAD STUFF\n\tat com.jaketschwartz");
    }

    /**
     * Determines if the expected output matches the actual Logger output by auto-formatting the input to regex values.
     * This is to get around the fact that the logs have LocalDateTime stamps in them that will never match without
     * more powerful mock frameworks in use.
     * @param message The message to output on failure.
     * @param logLevel The log level being used in the test.
     * @param expectedString The expected output to see from our Logger.
     */
    private void assertOutputMatches(final String message, final String logLevel, final String expectedString) {
        final String expectedOutput = String.format("\\[%5.5s]\\[.*]\\[com.jaketschwartz.…TestLoggerI] - %s",
                                                    logLevel,
                                                    expectedString);
        assertTrue(String.format("%s\n\nEXPECTED REGEX: %s\n\nWAS: %s", message, expectedOutput, capturedOut.toString().trim()),
                Pattern.compile(expectedOutput, Pattern.DOTALL).matcher(capturedOut.toString().trim()).find());
    }
}
