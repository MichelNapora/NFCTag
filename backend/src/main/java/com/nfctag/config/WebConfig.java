package com.nfctag.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS pour le développement : le front Angular (localhost:4200) appelle l'API (localhost:8080).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final NfctagProperties properties;

    public WebConfig(NfctagProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(properties.getPublicBaseUrl())
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
