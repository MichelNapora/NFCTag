package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class ForgottenPresenceCloser {

    @Autowired
    private PresenceRepository presenceRepository;

    @Value("${nfctag.estimated-duration-minutes}")
    private long estimatedDurationMinutes;

    @Scheduled(fixedRateString = "${nfctag.job-interval-ms}")
    public void closeForgottenPresences(){
        OffsetDateTime now = OffsetDateTime.now();
        List<Presence> openPresences = presenceRepository.findByDepartedAtIsNull();

        for (Presence presence : openPresences) {
            OffsetDateTime estimatedDeparture = presence.getArrivedAt().plusMinutes(estimatedDurationMinutes);

            if (now.isAfter(estimatedDeparture)) {
                presence.setDepartedAt(estimatedDeparture);
                presence.setEstimated(true);
                presenceRepository.save(presence);
            }
        }
    }
}