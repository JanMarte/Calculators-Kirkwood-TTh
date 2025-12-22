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

    /**
     * Converts a temperature from one scale to another.
     * This method is used by the Unit Tests and the start() method.
     *
     * @param degreesStr      The temperature value as a String.
     * @param currentScaleStr The current scale code (e.g. "C").
     * @param targetScaleStr  The target scale code (e.g. "F").
     * @return The converted temperature as a double.
     */
    public double convert(String degreesStr, String currentScaleStr, String targetScaleStr) {
        // Validate inputs are not null/empty
        if (degreesStr == null || degreesStr.isBlank()) {
            throw new IllegalArgumentException("Degrees input cannot be empty.");
        }
        if (currentScaleStr == null || currentScaleStr.isBlank()) {
            throw new IllegalArgumentException("Current scale input cannot be empty.");
        }
        if (targetScaleStr == null || targetScaleStr.isBlank()) {
            throw new IllegalArgumentException("Target scale input cannot be empty.");
        }

        // Parse the number
        double degrees;
        try {
            degrees = Double.parseDouble(degreesStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for degrees.");
        }

        // Create Model and Convert
        char currentScale = currentScaleStr.charAt(0);
        char targetScale = targetScaleStr.charAt(0);

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
            String degreesStr = "";
            String currentScaleStr = "";

            // Loop until we get a valid Temperature combination (Degrees + Scale)
            // This prevents moving on if the temperature is below absolute zero
            while(true) {
                // 1. Get Valid Number
                degreesStr = getValidDoubleString("Enter temperature value");

                // 2. Get Valid Scale
                currentScaleStr = getValidScaleInput("Enter current scale (C, F, K)");

                // 3. Logic Validation (Check Absolute Zero)
                try {
                    double d = Double.parseDouble(degreesStr);
                    char c = currentScaleStr.charAt(0);
                    // Attempt to create the object to trigger the absolute zero check
                    new JanTemperature(d, c);
                    // If we get here, the input is valid
                    break;
                } catch (IllegalArgumentException e) {
                    UIUtility.displayError(e.getMessage());
                    UIUtility.displayMessage("Please re-enter the temperature and scale.");
                }
            }

            // 4. Get Target Scale (Only asked if the previous steps were valid)
            String targetScaleStr = getValidScaleInput("Enter target scale (C, F, K)");

            try {
                // Perform Conversion
                double result = app.convert(degreesStr, currentScaleStr, targetScaleStr);

                // Display Result
                String symbol = targetScaleStr.substring(0, 1).toUpperCase();
                UIUtility.displaySuccess(String.format("Result: %.2f %s", result, symbol));

            } catch (IllegalArgumentException e) {
                UIUtility.displayError(e.getMessage());
            }

            // Loop Check
            if (!UserInput.getBoolean("Perform another conversion?")) {
                break;
            }
        }

        Messages.janGoodbye();
    }

    /**
     * loops until the user enters a valid number.
     * @return the valid number as a String.
     */
    private static String getValidDoubleString(String prompt) {
        while (true) {
            String input = UserInput.getString(prompt);
            try {
                Double.parseDouble(input);
                return input; // It's valid, return it
            } catch (NumberFormatException e) {
                UIUtility.displayError("Invalid input. Please enter a numeric value.");
            }
        }
    }

    /**
     * loops until the user enters C, F, or K.
     * @return the valid scale string.
     */
    private static String getValidScaleInput(String prompt) {
        while (true) {
            String input = UserInput.getString(prompt);
            if (input != null && input.length() > 0) {
                char c = Character.toUpperCase(input.charAt(0));
                if (c == 'C' || c == 'F' || c == 'K') {
                    return input;
                }
            }
            UIUtility.displayError("Invalid scale. Please enter C, F, or K.");
        }
    }
}