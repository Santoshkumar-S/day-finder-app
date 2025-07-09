package com.dayfinder.controller;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow frontend fetch to work
public class DayController {

    @GetMapping("/day")
    public Map<String, String> getDayFromDate(@RequestParam String date) {
        Map<String, String> response = new HashMap<>();
        response.put("input", date);

        try {
            // Parse DD-MM-YYYY format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            DayOfWeek day = parsedDate.getDayOfWeek();
            String formattedDay = capitalize(day.toString());

            response.put("day", formattedDay);
        } catch (Exception e) {
            response.put("error", "Invalid date format. Use DD-MM-YYYY.");
        }

        return response;
    }

    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
