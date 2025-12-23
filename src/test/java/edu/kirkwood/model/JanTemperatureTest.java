package edu.kirkwood.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * JUnit test class for the JanTemperature model.
 * Tests constructors, conversions, and exception handling.
 */
public class JanTemperatureTest {

    private static final double DELTA = 0.01;

    // Testing Default Constructor
    @Test
    void testDefaultConstructor() {
        JanTemperature t = new JanTemperature();
        assertEquals(0.0, t.getDegrees(), DELTA);
        assertEquals('C', t.getScale());
    }

    // Testing Parameterized Constructor with Valid Inputs
    @Test
    void testParameterizedConstructorValid() {
        JanTemperature t = new JanTemperature(100, 'C');
        assertEquals(100.0, t.getDegrees(), DELTA);
        assertEquals('C', t.getScale());
    }

    // Testing Celsius to Fahrenheit Conversion
    @Test
    void testToFahrenheitFromCelsius() {
        JanTemperature t = new JanTemperature(0, 'C');
        assertEquals(32.0, t.toFahrenheit(), DELTA);
    }

    // Testing Fahrenheit to Celsius Conversion
    @Test
    void testToCelsiusFromFahrenheit() {
        JanTemperature t = new JanTemperature(32, 'F');
        assertEquals(0.0, t.toCelsius(), DELTA);
    }

    // Testing Celsius to Kelvin Conversion
    @Test
    void testToKelvinFromCelsius() {
        JanTemperature t = new JanTemperature(0, 'C');
        assertEquals(273.15, t.toKelvin(), DELTA);
    }

    // Testing Kelvin to Celsius Conversion
    @Test
    void testBelowAbsoluteZeroCelsius() {
        assertThrows(IllegalArgumentException.class, () -> {
            new JanTemperature(-274, 'C');
        });
    }

    // Testing Fahrenheit to Kelvin Conversion
    @Test
    void testInvalidScale() {
        assertThrows(IllegalArgumentException.class, () -> {
            new JanTemperature(100, 'X');
        });
    }

    // Testing Setter Exception
    @Test
    void testToString() {
        JanTemperature t = new JanTemperature(98.6, 'F');
        assertEquals("98.60 F", t.toString());
    }
}