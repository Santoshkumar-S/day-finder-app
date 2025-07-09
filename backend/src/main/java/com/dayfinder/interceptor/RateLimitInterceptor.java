package com.dayfinder.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final ConcurrentHashMap<String, RequestCounter> requestCounters = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private static final int WINDOW_SIZE_MINUTES = 1;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String clientIp = getClientIp(request);
        RequestCounter counter = requestCounters.computeIfAbsent(clientIp, k -> new RequestCounter());

        if (counter.isRateLimited()) {
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Rate limit exceeded. Please try again later.");
            return false;
        }

        counter.increment();
        return true;
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private static class RequestCounter {
        private final AtomicInteger count = new AtomicInteger(0);
        private LocalDateTime windowStart = LocalDateTime.now();

        public boolean isRateLimited() {
            LocalDateTime now = LocalDateTime.now();
            if (ChronoUnit.MINUTES.between(windowStart, now) >= WINDOW_SIZE_MINUTES) {
                count.set(0);
                windowStart = now;
                return false;
            }
            return count.get() >= MAX_REQUESTS_PER_MINUTE;
        }

        public void increment() {
            count.incrementAndGet();
        }
    }
}