package com.dayfinder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayResponse {
    private String input;
    private String day;
    private String error;
    private LocalDateTime timestamp;
    private boolean success;

    public DayResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public static DayResponse success(String input, String day) {
        DayResponse response = new DayResponse();
        response.input = input;
        response.day = day;
        response.success = true;
        return response;
    }

    public static DayResponse error(String input, String error) {
        DayResponse response = new DayResponse();
        response.input = input;
        response.error = error;
        response.success = false;
        return response;
    }

    // Getters and Setters
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}