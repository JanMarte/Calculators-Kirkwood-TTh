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
        // 1. Validate inputs are not null/empty
        if (degreesStr == null || degreesStr.isBlank()) {
            throw new IllegalArgumentException("Degrees input cannot be empty.");
        }
        if (currentScaleStr == null || currentScaleStr.isBlank()) {
            throw new IllegalArgumentException("Current scale input cannot be empty.");
        }
        if (targetScaleStr == null || targetScaleStr.isBlank()) {
            throw new IllegalArgumentException("Target scale input cannot be empty.");
        }

        // 2. Parse the number
        double degrees;
        try {
            degrees = Double.parseDouble(degreesStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for degrees.");
        }

        // 3. Create Model and Convert
        // We take the first char of the scale string (e.g., "Celsius" -> 'C')
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
        // Create an instance so we can use the 'convert' method
        JanTemperatureCalculator app = new JanTemperatureCalculator();

        Messages.janGreet();

        while (true) {
            // 1. Get Input as Strings
            // We use getString so we can pass it to 'convert' which handles the validation
            String degreesStr = UserInput.getString("Enter temperature value");
            String currentScaleStr = UserInput.getString("Enter current scale (C, F, K)");
            String targetScaleStr = UserInput.getString("Enter target scale (C, F, K)");

            try {
                // 2. Perform Conversion using the logic method
                double result = app.convert(degreesStr, currentScaleStr, targetScaleStr);

                // 3. Display Result
                // Extract the symbol for display (C, F, or K)
                String symbol = targetScaleStr.substring(0, 1).toUpperCase();
                UIUtility.displaySuccess(String.format("Result: %.2f %s", result, symbol));

            } catch (IllegalArgumentException e) {
                // Display any errors from the 'convert' method or Model
                UIUtility.displayError(e.getMessage());
            }

            // 4. Loop Check
            if (!UserInput.getBoolean("Perform another conversion?")) {
                break;
            }
        }

        Messages.janGoodbye();
    }
}