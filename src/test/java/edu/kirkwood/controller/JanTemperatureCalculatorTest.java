package edu.kirkwood.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test class for the TemperatureCalculator controller.
 * Verifies parsing logic, valid conversions, and exception handling.
 */
public class JanTemperatureCalculatorTest {

    private final JanTemperatureCalculator calculator = new JanTemperatureCalculator();
    private static final double DELTA = 0.01;

    // Test Valid Conversion: Celsius to Fahrenheit
    @Test
    void testParseValidCelsiusToFahrenheit() {
        String input = "0 C F";
        double result = calculator.parseAndConvert(input);
        assertEquals(32.0, result, DELTA, "0 C should match 32 F");
    }

    // Test Valid Conversion: Fahrenheit to Kelvin
    @Test
    void testParseValidFahrenheitToKelvin() {
        String input = "32 F K";
        double result = calculator.parseAndConvert(input);
        assertEquals(273.15, result, DELTA, "32 F should match 273.15 K");
    }

    // Test Invalid Number Input
    @Test
    void testParseInvalidNumberFormat() {
        String input = "abc C F";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert(input);
        });
        assertEquals("Invalid number format for degrees.", exception.getMessage());
    }

    // Test Null Input
    @Test
    void testParseEmptyInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert("");
        });
        assertEquals("Input cannot be empty.", exception.getMessage());
    }

    // Test Invalid Target Scale Character
    @Test
    void testParseInvalidTargetScale() {
        String input = "100 C X";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert(input);
        });
        assertEquals("Invalid target scale: Must be 'C', 'F', or 'K'.", exception.getMessage());
    }

    // Test Model Constraint Propagation (Below Absolute Zero)
    @Test
    void testParseBelowAbsoluteZero() {
        String input = "-500 F C";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert(input);
        });
        assertEquals("Temperature cannot be below absolute zero.", exception.getMessage());
    }
}