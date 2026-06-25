package com.nfctag.presence;

import com.nfctag.config.NfctagProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Ferme automatiquement les présences laissées ouvertes : quand un technicien
 * oublie de scanner en partant, l'intervention est estimée à
 * {@code estimatedDurationMinutes} (1h par défaut) après l'arrivée.
 */
@Component
public class PresenceClosingJob {

    private static final Logger log = LoggerFactory.getLogger(PresenceClosingJob.class);

    private final PresenceRepository presenceRepository;
    private final NfctagProperties properties;

    public PresenceClosingJob(PresenceRepository presenceRepository, NfctagProperties properties) {
        this.presenceRepository = presenceRepository;
        this.properties = properties;
    }

    /** Toutes les 5 minutes. */
    @Scheduled(fixedDelayString = "PT5M")
    @Transactional
    public void closeStalePresences() {
        int minutes = properties.getEstimatedDurationMinutes();
        OffsetDateTime threshold = OffsetDateTime.now().minusMinutes(minutes);

        List<Presence> stale = presenceRepository.findByDepartedAtIsNullAndArrivedAtBefore(threshold);
        if (stale.isEmpty()) {
            return;
        }

        for (Presence p : stale) {
            p.setDepartedAt(p.getArrivedAt().plusMinutes(minutes));
            p.setEstimated(true);
            p.setUpdated(OffsetDateTime.now());
        }
        presenceRepository.saveAll(stale);
        log.info("Fermeture estimée de {} présence(s) ouverte(s) (>{} min).", stale.size(), minutes);
    }
}
