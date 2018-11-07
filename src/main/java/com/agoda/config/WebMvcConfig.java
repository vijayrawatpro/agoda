package com.agoda.config;

import com.agoda.domain.Rate;
import com.agoda.interceptor.RateLimitingInterceptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    RateLimitingInterceptor rateLimitingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitingInterceptor);
    }

    @Bean
    public Map<String, Rate> rateMap() {
        String property = env.getProperty("key.rate.limiting");
        Map<String, Rate> map = new HashMap<>();
        String[] items = property.split(",");
        Map<String, Rate> rateMap = Arrays.stream(items)
            .map(item -> item.split(":"))
            .collect(Collectors.toMap(e -> e[0], e -> new Rate(Integer.valueOf(e[1]), Integer.valueOf(e[2]))));
        return rateMap;
    }

    @Bean
    public Rate globalRate() {
        String property = env.getProperty("global.rate.limiting");
        String[] items = property.split(":");
        return new Rate(Integer.valueOf(items[0]), Integer.valueOf(items[1]));
    }
}