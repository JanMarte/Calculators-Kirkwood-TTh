package edu.kirkwood.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * JUnit test class for the JanTemperature model.
 * Tests constructors, conversions, and exception handling.
 */
public class JanTemperatureTest {

    private static final double DELTA = 0.01; // Tolerance for floating-point comparisons

    // Testing Default Constructor
    @Test
    public void testDefaultConstructor() {
        JanTemperature t = new JanTemperature();
        assertEquals(0.0, t.getDegrees(), DELTA);
        assertEquals('C', t.getScale());
    }

    // Testing Parameterized Constructor with Valid Inputs
    @Test
    public void testParameterizedConstructorValid() {
        JanTemperature t = new JanTemperature(100, 'C');
        assertEquals(100.0, t.getDegrees(), DELTA);
        assertEquals('C', t.getScale());
    }

    // Testing Celsius to Fahrenheit Conversion
    @Test
    public void testToFahrenheitFromCelsius() {
        JanTemperature t = new JanTemperature(0, 'C'); // Freezing point
        assertEquals(32.0, t.toFahrenheit(), DELTA);

        t.setDegrees(100); // Boiling point
        assertEquals(212.0, t.toFahrenheit(), DELTA);
    }

    // Testing Fahrenheit to Celsius Conversion
    @Test
    public void testToCelsiusFromFahrenheit() {
        JanTemperature t = new JanTemperature(32, 'F');
        assertEquals(0.0, t.toCelsius(), DELTA);
    }

    // Testing Celsius to Kelvin Conversion
    @Test
    public void testToKelvinFromCelsius() {
        JanTemperature t = new JanTemperature(0, 'C');
        assertEquals(273.15, t.toKelvin(), DELTA);
    }

    // Testing Kelvin to Celsius Conversion
    @Test
    public void testToCelsiusFromKelvin() {
        JanTemperature t = new JanTemperature(273.15, 'K');
        assertEquals(0.0, t.toCelsius(), DELTA);
    }

    // Testing Fahrenheit to Kelvin Conversion
    @Test
    public void testToKelvinFromFahrenheit() {
        JanTemperature t = new JanTemperature(32, 'F');
        assertEquals(273.15, t.toKelvin(), DELTA);
    }

    // Testing Identity Conversion (F to F)
    @Test
    public void testToFahrenheitFromFahrenheit() {
        JanTemperature t = new JanTemperature(50, 'F');
        assertEquals(50.0, t.toFahrenheit(), DELTA);
    }

    // Testing Exception: Below Absolute Zero (Celsius)
    @Test
    public void testBelowAbsoluteZeroCelsius() {
        try {
            new JanTemperature(-274, 'C'); // -273.15 is the limit
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Test passed: Exception was caught
        }
    }

    // Testing Exception: Below Absolute Zero (Kelvin)
    @Test
    public void testBelowAbsoluteZeroKelvin() {
        try {
            new JanTemperature(-1, 'K'); // 0 is the limit
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Test passed: Exception was caught
        }
    }

    // Testing Exception: Invalid Scale Character
    @Test
    public void testInvalidScale() {
        try {
            new JanTemperature(100, 'X');
            fail("Expected IllegalArgumentException for invalid scale");
        } catch (IllegalArgumentException e) {
            // Test passed: Exception was caught
        }
    }

    // Testing Setter Exception
    @Test
    public void testSetDegreesInvalid() {
        JanTemperature t = new JanTemperature(0, 'K');
        try {
            t.setDegrees(-5); // Should throw exception
            fail("Expected IllegalArgumentException for invalid degree setter");
        } catch (IllegalArgumentException e) {
            // Test passed: Exception was caught
        }
    }

    // Testing ToString formatting
    @Test
    public void testToString() {
        JanTemperature t = new JanTemperature(98.6, 'F');
        assertEquals("98.60 F", t.toString());
    }
}