package com.dayfinder.service;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;
import java.util.regex.Pattern;

@Service
public class DayService {

    private static final Logger logger = LoggerFactory.getLogger(DayService.class);
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Cacheable(value = "dayCache", key = "#date")
    public String getDayFromDate(String date) {
        logger.info("Calculating day for date: {}", date);

        // Validate input format
        if (!isValidDateFormat(date)) {
            logger.warn("Invalid date format received: {}", date);
            throw new IllegalArgumentException("Invalid date format. Use DD-MM-YYYY.");
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date, FORMATTER);

            // Validate date range (reasonable bounds)
            if (parsedDate.getYear() < 1900 || parsedDate.getYear() > 2100) {
                logger.warn("Date out of reasonable range: {}", date);
                throw new IllegalArgumentException("Date must be between 1900 and 2100.");
            }

            DayOfWeek day = parsedDate.getDayOfWeek();
            String formattedDay = capitalize(day.toString());

            logger.info("Successfully calculated day: {} for date: {}", formattedDay, date);
            return formattedDay;

        } catch (DateTimeParseException e) {
            logger.error("Failed to parse date: {}", date, e);
            throw new IllegalArgumentException("Invalid date. Please check the date format and validity.");
        }
    }

    private boolean isValidDateFormat(String date) {
        return date != null && DATE_PATTERN.matcher(date).matches();
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}