package com.nfctag.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nfctag")
public class NfctagProperties {

    /** Durée estimée (minutes) quand le technicien oublie de scanner en partant. */
    private int estimatedDurationMinutes = 60;

    /** URL publique du front, pour générer l'URL inscrite sur les tags. */
    private String publicBaseUrl = "http://localhost:4200";

    public int getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    public void setEstimatedDurationMinutes(int estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }

    public String getPublicBaseUrl() {
        return publicBaseUrl;
    }

    public void setPublicBaseUrl(String publicBaseUrl) {
        this.publicBaseUrl = publicBaseUrl;
    }
}
