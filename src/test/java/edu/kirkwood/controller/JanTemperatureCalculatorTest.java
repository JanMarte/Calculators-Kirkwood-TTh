package edu.kirkwood.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test class for the TemperatureCalculator controller.
 * Verifies parsing logic, valid conversions, and exception handling.
 */
public class JanTemperatureCalculatorTest {

    private final JanTemperatureCalculator calculator = new JanTemperatureCalculator();
    private static final double DELTA = 0.01;

    // 1. Test Valid Conversion: Celsius to Fahrenheit (Happy Path)
    @Test
    public void testConvertCelsiusToFahrenheit() {
        // Arrange
        String degrees = "0";
        String currentScale = "C";
        String targetScale = "F";

        // Act
        double result = calculator.convert(degrees, currentScale, targetScale);

        // Assert
        assertEquals(32.0, result, DELTA, "0 C should match 32 F");
    }

    // 2. Test Valid Conversion: Fahrenheit to Kelvin (Happy Path)
    @Test
    public void testConvertFahrenheitToKelvin() {
        // Arrange
        String degrees = "32";
        String currentScale = "F";
        String targetScale = "K";

        // Act
        double result = calculator.convert(degrees, currentScale, targetScale);

        // Assert
        assertEquals(273.15, result, DELTA, "32 F should match 273.15 K");
    }

    // 3. Test Invalid Number Input (Non-numeric string)
    @Test
    public void testInvalidNumberFormat() {
        // Arrange
        String degrees = "abc";
        String currentScale = "C";
        String targetScale = "F";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.convert(degrees, currentScale, targetScale);
        });
        assertEquals("Invalid number format for degrees.", exception.getMessage());
    }

    // 4. Test Null Input
    @Test
    public void testNullInput() {
        // Arrange
        String degrees = null;
        String currentScale = "C";
        String targetScale = "F";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.convert(degrees, currentScale, targetScale);
        });
        assertEquals("Degrees input cannot be empty.", exception.getMessage());
    }

    // 5. Test Invalid Target Scale Character
    @Test
    public void testInvalidTargetScale() {
        // Arrange
        String degrees = "100";
        String currentScale = "C";
        String targetScale = "X"; // Invalid

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.convert(degrees, currentScale, targetScale);
        });
        assertEquals("Invalid target scale: Must be 'C', 'F', or 'K'.", exception.getMessage());
    }

    // 6. Test Model Constraint Propagation (Below Absolute Zero)
    // This ensures the Controller correctly allows the Model to throw its own exceptions
    @Test
    public void testBelowAbsoluteZero() {
        // Arrange
        String degrees = "-500";
        String currentScale = "F"; // -500 F is below absolute zero
        String targetScale = "C";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.convert(degrees, currentScale, targetScale);
        });
        // The message comes from the JanTemperature model class
        assertEquals("JanTemperature cannot be below absolute zero.", exception.getMessage());
    }
}