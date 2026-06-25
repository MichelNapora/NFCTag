package com.nfctag.presence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PresenceRepository extends JpaRepository<Presence, Long> {

    /** Présence ouverte (sans départ) pour ce technicien sur ce tag. */
    Optional<Presence> findFirstByWorkerIdAndNfcIdAndDepartedAtIsNull(Long workerId, Long nfcId);

    /** Toutes les présences encore ouvertes dont l'arrivée est antérieure au seuil. */
    List<Presence> findByDepartedAtIsNullAndArrivedAtBefore(OffsetDateTime threshold);

    List<Presence> findAllByOrderByArrivedAtDesc();
}
