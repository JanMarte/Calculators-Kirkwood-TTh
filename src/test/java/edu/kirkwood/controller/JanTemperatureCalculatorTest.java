package edu.kirkwood.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    // Valid Negative Number (e.g., -40 C is -40 F)
    @Test
    void testParseValidNegativeNumber() {
        String input = "-40 C F";
        double result = calculator.parseAndConvert(input);
        assertEquals(-40.0, result, DELTA, "-40 C should be exactly -40 F");
    }

    // Extremely Large Number
    @Test
    void testParseExtremelyLargeNumber() {
        // 1 Billion degrees (Now exceeds the 1 Million limit)
        String input = "1000000000 C K";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert(input);
        });

        // Verify the error message mentions the size limit
        assertTrue(exception.getMessage().contains("Value too large"));
    }

    // String with Mixed Letters and Numbers (e.g. "12a3")
    @Test
    void testParseMixedAlphaNumeric() {
        String input = "12a3 F C";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert(input);
        });
        assertEquals("Invalid number format for degrees.", exception.getMessage());
    }

    // Empty Input (Whitespace only)
    @Test
    void testParseWhitespaceOnly() {
        String input = "   ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.parseAndConvert(input);
        });
        assertEquals("Input cannot be empty.", exception.getMessage());
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