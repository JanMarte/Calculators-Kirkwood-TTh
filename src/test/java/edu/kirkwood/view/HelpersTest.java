package edu.kirkwood.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static edu.kirkwood.view.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;

class HelpersTest {

    // Emilia
    @Test
    void isNullString() {
        // Arrange
        String actual = null;
        Boolean expected = false;
        // Act + Assert
        assertEquals(expected, isValidString(actual));
    }

    // Emilia
    @Test
    void isEmptyString() {
        // Arrange
        String actual = "";
        Boolean expected = false;
        // Act + Assert
        assertEquals(expected, isValidString(actual));
    }

    // Emilia
    @Test
    void stringIsValid() {
        // Arrange
        String actual = "Hooray!";
        Boolean expected = true;
        // Act + Assert
        assertEquals(expected, isValidString(actual));
    }

    // Celeste
    @Test
    void testToCurrencyWithWholeNumberAndDecimal(){
        double input = 1.23;
        String expected = "$1.23";

        String actual = toCurrency(input);

        assertEquals(expected, actual);
    }

    // Celeste
    @Test
    void testToCurrencyWithNegativeNumber(){
        double input = -1.23;
        String expected = "-$1.23";

        String actual = toCurrency(input);

        assertEquals(expected, actual);
    }

    // Mouftaou
    @Test
    void toCurrencyIIWithZeroDecimal() {
        double input = 8;
        String expected = "$8.00";
        String actual = toCurrency(input);
        assertEquals(expected, actual);
    }

    // Mouftaou
    @Test
    void toCurrencyIIWithWThreeDecimalRoundUp() {
        double input = 567.236;
        String expected = "$567.24";
        String actual = toCurrency(input);
        assertEquals(expected, actual);
    }

    // Camren
    @Test
    void roundRoundsToTwoDecimalPlacesCorrectly() {
        // Arrange
        double input = 3.14159;
        int places = 2;
        double input2 = 3.14159;
        int places2 = 4;

        // Act
        String result = Helpers.round(input, places);
        String result2 = Helpers.round(input2, places2);

        // Assert
        assertEquals("3.14", result);
        assertEquals("3.1416", result2);
    }

    // Camren
    @Test
    void roundStripsTrailingZerosAfterRounding() {
        // Arrange
        double input = 2.0;
        int places = 3;


        // Act
        String result = Helpers.round(input, places);


        // Assert
        assertEquals("2", result);

    }

    // Asle
    @Test
    void testIsDateInPastReturnsTrueForPastDate() {
        LocalDate pastDate = LocalDate.of(2004, Month.of(5), 28);
        boolean expected = true;
        boolean actual = isDateInThePast(pastDate);

        assertEquals(expected, actual);
    }

    // Asle
    @Test
    void testIsDateInPastReturnsFalseForFutureDate() {
        LocalDate futureDate = LocalDate.of(2088, Month.of(5), 28);
        boolean expected = false;
        boolean actual = isDateInThePast(futureDate);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test isDateInThePast with today's date")
    void testIsDateInThePastGivenToday() {
        // Arrange
        LocalDate today = LocalDate.now();

        // Act
        boolean expected = false;
        boolean actual = Helpers.isDateInThePast(today);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test isDateInThePast with an invalid date")
    void testIsDateInThePastGivenInvalidDate() {
        // Arrange
        LocalDate date = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> Helpers.isDateInThePast(date));
    }

    // Jan's Work
    public static boolean isValidString(String str) {
        return str != null && !str.equals("");
    }

    public static String round(double number, int numDecPlaces) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(numDecPlaces, RoundingMode.HALF_UP).stripTrailingZeros();
        return bigDecimal.toString();
    }

    public static String toCurrency(double amt) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amt);
    }

    public static String formatDateLong(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        return dateFormatOutput.format(date);
    }

    public static String formatDateShort(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("M/d/yyyy");
        return dateFormatOutput.format(date);
    }

    public static boolean isDateInThePast(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return date.isBefore(LocalDate.now());
    }

    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("None of the dates can be null");
        }
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }
}