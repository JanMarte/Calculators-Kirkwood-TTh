package edu.kirkwood.controller;

import edu.kirkwood.model.JanTemperature;
import edu.kirkwood.view.Messages;
import edu.kirkwood.view.UIUtility;
import edu.kirkwood.view.UserInput;

/**
 * Controller class for the JanTemperature Calculator.
 * Handles both the conversion logic (for tests) and the user interface loop (for the app).
 */
public class JanTemperatureCalculator {

    public double parseAndConvert(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        String[] parts = input.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format. Usage: 'Value Scale TargetScale' (e.g. 100 F C)");
        }

        double degrees;
        try {
            degrees = Double.parseDouble(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for degrees.");
        }

        if (parts[1].length() != 1 || parts[2].length() != 1) {
            throw new IllegalArgumentException("Scales must be single characters (C, F, K).");
        }

        char currentScale = parts[1].charAt(0);
        char targetScale = parts[2].charAt(0);

        JanTemperature temp = new JanTemperature(degrees, currentScale);

        switch (Character.toUpperCase(targetScale)) {
            case 'C':
                return temp.toCelsius();
            case 'F':
                return temp.toFahrenheit();
            case 'K':
                return temp.toKelvin();
            default:
                throw new IllegalArgumentException("Invalid target scale: Must be 'C', 'F', or 'K'.");
        }
    }

    /**
     * Starts the interactive user interface for this calculator.
     * Called by the MainMenu.
     */
    public static void start() {
        JanTemperatureCalculator app = new JanTemperatureCalculator();
        Messages.janGreet();

        while (true) {
            String input = UserInput.getString("Enter conversion");

            try {
                double result = app.parseAndConvert(input);
                String[] parts = input.trim().split("\\s+");
                String symbol = parts[2].toUpperCase();

                UIUtility.displaySuccess(String.format("Result: %.2f %s", result, symbol));

            } catch (IllegalArgumentException e) {
                UIUtility.displayError(e.getMessage());
            }

            if (!UserInput.getBoolean("Perform another conversion?")) {
                break;
            }
        }
        Messages.janGoodbye();
    }
}