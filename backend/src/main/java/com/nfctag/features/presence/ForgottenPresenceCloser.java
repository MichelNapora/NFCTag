package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class ForgottenPresenceCloser {

    @Autowired
    private PresenceRepository presenceRepository;

    @Value("${nfctag.estimated-duration-minutes}")
    private long estimatedDurationMinutes;

    @Value("${nfctag.timezone}")
    private String timezone;

    @Scheduled(fixedRateString = "${nfctag.job-interval-ms}")
    public void closeForgottenPresences(){
        ZoneId zone = ZoneId.of(timezone);
        LocalDate today = LocalDate.now(zone);

        List<Presence> openPresences = presenceRepository.findByDepartedAtIsNull();

        for (Presence presence : openPresences) {
            LocalDate arrivalDay = presence.getArrivedAt().atZoneSameInstant(zone).toLocalDate();

            if (arrivalDay.isBefore(today)) {
                presence.setDepartedAt(presence.getArrivedAt().plusMinutes(estimatedDurationMinutes));
                presence.setEstimated(true);
                presenceRepository.save(presence);
            }
        }
    }
}