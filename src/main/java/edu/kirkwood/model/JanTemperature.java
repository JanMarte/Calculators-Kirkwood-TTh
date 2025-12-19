package edu.kirkwood.model;

/**
 * Represents a temperature value and provides utility methods for
 * converting between Celsius, Fahrenheit, and Kelvin scales.
 * This class ensures that no temperature below absolute zero can be cr2cani eated.
 */
public class JanTemperature {

    private double degrees;
    private final char scale; // 'C', 'F', or 'K'

    /**
     * Default constructor setting temperature to 0 degrees Celsius.
     */
    public JanTemperature() {
        this.degrees = 0;
        this.scale = 'C';
    }

    /**
     * Constructs a JanTemperature object with a specific degree and scale.
     * * @param degrees The temperature value.
     * @param scale   The scale of the temperature ('C', 'F', or 'K').
     * @throws IllegalArgumentException if the temperature is below absolute zero
     * or if an invalid scale character is provided.
     */
    public JanTemperature(double degrees, char scale) {
        if (!isValidScale(scale)) {
            throw new IllegalArgumentException("Invalid scale: Must be 'C', 'F', or 'K'.");
        }
        if (isBelowAbsoluteZero(degrees, scale)) {
            throw new IllegalArgumentException("JanTemperature cannot be below absolute zero.");
        }
        this.degrees = degrees;
        this.scale = Character.toUpperCase(scale);
    }

    /**
     * Gets the degree value of the temperature.
     * * @return the degree value.
     */
    public double getDegrees() {
        return degrees;
    }

    /**
     * Sets the degree value of the temperature.
     * * @param degrees The new temperature value.
     * @throws IllegalArgumentException if the new value is below absolute zero for the current scale.
     */
    public void setDegrees(double degrees) {
        if (isBelowAbsoluteZero(degrees, this.scale)) {
            throw new IllegalArgumentException("JanTemperature cannot be below absolute zero.");
        }
        this.degrees = degrees;
    }

    /**
     * Gets the scale of the temperature.
     * * @return the scale char ('C', 'F', or 'K').
     */
    public char getScale() {
        return scale;
    }

    /**
     * Converts the current temperature to Celsius.
     * * @return The temperature in degrees Celsius.
     */
    public double toCelsius() {
        if (scale == 'C') return degrees;
        if (scale == 'F') return (degrees - 32) * 5.0 / 9.0;
        return degrees - 273.15; // Kelvin to Celsius
    }

    /**
     * Converts the current temperature to Fahrenheit.
     * * @return The temperature in degrees Fahrenheit.
     */
    public double toFahrenheit() {
        if (scale == 'F') return degrees;
        if (scale == 'C') return (degrees * 9.0 / 5.0) + 32;
        return (degrees - 273.15) * 9.0 / 5.0 + 32; // Kelvin to Fahrenheit
    }

    /**
     * Converts the current temperature to Kelvin.
     * * @return The temperature in Kelvin.
     */
    public double toKelvin() {
        if (scale == 'K') return degrees;
        if (scale == 'C') return degrees + 273.15;
        return (degrees - 32) * 5.0 / 9.0 + 273.15; // Fahrenheit to Kelvin
    }

    /**
     * Helper method to validate the scale character.
     * * @param s The scale character to check.
     * @return true if valid, false otherwise.
     */
    private boolean isValidScale(char s) {
        char upper = Character.toUpperCase(s);
        return upper == 'C' || upper == 'F' || upper == 'K';
    }

    /**
     * Helper method to check if a value is below absolute zero.
     * * @param deg The degrees to check.
     * @param s   The scale to check against.
     * @return true if below absolute zero, false otherwise.
     */
    private boolean isBelowAbsoluteZero(double deg, char s) {
        char upper = Character.toUpperCase(s);
        if (upper == 'C') return deg < -273.15;
        if (upper == 'F') return deg < -459.67;
        if (upper == 'K') return deg < 0;
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", degrees, scale);
    }
}