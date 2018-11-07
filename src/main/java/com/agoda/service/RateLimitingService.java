package com.agoda.service;

import com.agoda.domain.Rate;
import com.agoda.domain.RateLimiter;
import com.agoda.exception.RateLimitExceededException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RateLimitingService {

    private Map<String, RateLimiter> map = new HashMap<>();

    public synchronized boolean allow(String apiKey, Rate rate) throws RateLimitExceededException {
        RateLimiter rateLimiter = map.get(apiKey);
        if (rateLimiter == null) {
            rateLimiter = new RateLimiter(rate);
        }
        map.put(apiKey, rateLimiter);
        boolean gotAccess = rateLimiter.getAccess();
        if (!gotAccess) {
            throw new RateLimitExceededException("Rate limit exceeded wait for 5 minutes");
        }
        return true;
    }

    //required for testing
    public synchronized void cleanUp() {
        for (Map.Entry<String, RateLimiter> entry: map.entrySet()) {
            entry.getValue().cleanUp();
        }
        map = new HashMap<>();
    }
}
