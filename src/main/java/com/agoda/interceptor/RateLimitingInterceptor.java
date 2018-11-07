package com.agoda.interceptor;

import com.agoda.domain.Rate;
import com.agoda.exception.RateLimitExceededException;
import com.agoda.service.RateLimitingService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RateLimitingInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RateLimitingService rateLimitingService;

    @Autowired
    @Qualifier("rateMap")
    private Map<String, Rate> rateMap;

    @Autowired
    @Qualifier("globalRate")
    private Rate globalRate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws RateLimitExceededException {
        String apiKey = request.getHeader("X-API-KEY");
        Rate rate = rateMap.get(apiKey);
        if (rate == null) {
            rate = globalRate;
        }
        rateLimitingService.allow(apiKey, rate);
        return true;
    }
}