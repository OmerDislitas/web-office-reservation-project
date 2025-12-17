package com.officerental.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
                
                registry.addMapping("/login")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("POST", "OPTIONS")
                        .allowCredentials(true);
                
                registry.addMapping("/logout")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("POST", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
