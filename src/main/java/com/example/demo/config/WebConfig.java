package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private @NonNull AdminCheckInterceptor adminCheckInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) adminCheckInterceptor)
                .addPathPatterns(
                    "/movies/**",
                    "/users/**",
                    "/theaters/**",
                    "/showtimes/**",
                    "/bookings/**"
                )
                .excludePathPatterns(
                    "/",
                    "/movie/**",
                    "/book/**",
                    "/login",
                    "/register",
                    "/logout",
                    "/my-bookings"
                );
    }
}