package com.dayfinder.controller;

import com.dayfinder.service.DayService;
import com.dayfinder.dto.DayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DayController {

    private static final Logger logger = LoggerFactory.getLogger(DayController.class);

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping("/day")
    public ResponseEntity<DayResponse> getDayFromDate(@RequestParam String date) {
        logger.info("Received request for date: {}", date);

        try {
            String day = dayService.getDayFromDate(date);
            DayResponse response = DayResponse.success(date, day);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            DayResponse response = DayResponse.error(date, e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            logger.error("Unexpected error processing date: {}", date, e);
            DayResponse response = DayResponse.error(date, "An unexpected error occurred. Please try again.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Day Finder API is running!");
    }
}
