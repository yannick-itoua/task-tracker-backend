package com.tasktracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow specific origins
        List<String> allowedOrigins = Arrays.asList(
            "http://localhost:3000",
            "http://localhost:5173",
            "https://localhost:3000",
            "https://localhost:5173"
        );
        
        // Get CORS_ORIGINS from environment variable
        String corsOrigins = System.getenv("CORS_ORIGINS");
        if (corsOrigins != null && !corsOrigins.isEmpty()) {
            String[] envOrigins = corsOrigins.split(",");
            for (String origin : envOrigins) {
                allowedOrigins.add(origin.trim());
            }
        }
        
        // Allow all Railway app domains
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:*",
            "https://localhost:*",
            "https://*.railway.app",
            "https://*.vercel.app",
            "https://*.netlify.app"
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}