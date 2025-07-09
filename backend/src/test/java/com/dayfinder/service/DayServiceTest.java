package com.dayfinder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DayServiceTest {

    private DayService dayService;

    @BeforeEach
    void setUp() {
        dayService = new DayService();
    }

    @Test
    @DisplayName("Should return correct day for valid date")
    void shouldReturnCorrectDayForValidDate() {
        // Test various dates
        assertEquals("Monday", dayService.getDayFromDate("25-12-2023"));
        assertEquals("Tuesday", dayService.getDayFromDate("26-12-2023"));
        assertEquals("Wednesday", dayService.getDayFromDate("27-12-2023"));
        assertEquals("Thursday", dayService.getDayFromDate("28-12-2023"));
        assertEquals("Friday", dayService.getDayFromDate("29-12-2023"));
        assertEquals("Saturday", dayService.getDayFromDate("30-12-2023"));
        assertEquals("Sunday", dayService.getDayFromDate("31-12-2023"));
    }

    @Test
    @DisplayName("Should handle leap year correctly")
    void shouldHandleLeapYearCorrectly() {
        assertEquals("Thursday", dayService.getDayFromDate("29-02-2024")); // Leap year
        assertEquals("Monday", dayService.getDayFromDate("29-02-2016")); // Leap year
    }

    @Test
    @DisplayName("Should throw exception for invalid date format")
    void shouldThrowExceptionForInvalidDateFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("2024-12-25"); // Wrong format
        });

        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("25/12/2024"); // Wrong separator
        });

        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("25-12-24"); // Short year
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid date")
    void shouldThrowExceptionForInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("32-12-2024"); // Invalid day
        });

        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("25-13-2024"); // Invalid month
        });

        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("29-02-2023"); // Non-leap year February 29
        });
    }

    @Test
    @DisplayName("Should throw exception for year out of range")
    void shouldThrowExceptionForYearOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("25-12-1899"); // Too old
        });

        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("25-12-2101"); // Too new
        });
    }

    @Test
    @DisplayName("Should handle null input")
    void shouldHandleNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate(null);
        });
    }

    @Test
    @DisplayName("Should handle empty input")
    void shouldHandleEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            dayService.getDayFromDate("");
        });
    }

    @Test
    @DisplayName("Should return consistent results for same date")
    void shouldReturnConsistentResultsForSameDate() {
        String date = "25-12-2024";
        String firstResult = dayService.getDayFromDate(date);
        String secondResult = dayService.getDayFromDate(date);

        assertEquals(firstResult, secondResult);
    }
}