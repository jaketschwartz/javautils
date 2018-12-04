package com.jaketschwartz.javautils.manipulation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestObjectConversionUtil {
    @Test
    public void testCastBadTypes() {
        assertFalse("You should not be able to cast an Integer to a String",
                    ObjectConversionUtil.cast(10, String.class).isPresent());
    }

    @Test
    public void testCastSameType() {
        final String input = "TEST";
        final String output = ObjectConversionUtil.cast(input, String.class).orElse(null);
        assertEquals("You should receive the same output as your input", input, output);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDowncastFailure() {
        final List<String> strings = new LinkedList<>();
        final ArrayList<String> arrayList = ObjectConversionUtil.downcast(strings, ArrayList.class).orElse(null);
        assertNull("We should have failed to downcast because we tried to downcast a LinkedList to an ArrayList",
                   arrayList);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDowncastSuccess() {
        final List<String> strings = new ArrayList<>();
        final ArrayList<String> arrayList = ObjectConversionUtil.downcast(strings, ArrayList.class).orElse(null);
        assertEquals("We should have received the same object because they are the same",
                strings, arrayList);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpcast() {
        final ArrayList<String> strings = new ArrayList<>();
        final List<String> normalList = ObjectConversionUtil.upcast(strings, List.class).orElse(null);
        assertEquals("We should have received a List successfully and they should be still equal",
                strings, normalList);
    }
}
