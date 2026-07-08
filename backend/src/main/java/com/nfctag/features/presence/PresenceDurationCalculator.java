package com.nfctag.features.presence;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PresenceDurationCalculator {
    public Long compute(Presence p) {
        if (p.getDepartedAt() != null) {
            return Duration.between(p.getArrivedAt(), p.getDepartedAt()).toMinutes();
        } else {
            return null;
        }
    }
}
