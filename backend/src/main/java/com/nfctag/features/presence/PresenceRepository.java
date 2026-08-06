package com.nfctag.features.presence;

import com.nfctag.features.location.LocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PresenceRepository extends JpaRepository<Presence, UUID>, JpaSpecificationExecutor<Presence> {
    List<Presence> findByTechnicianId(UUID technicianId);
    List<Presence> findByTechnicianMobile(String mobile);
    List<Presence> findByTagWingBuildingId(UUID buildingId);
    List<Presence> findByTagId(UUID tagId);
    Optional<Presence> findByTechnicianIdAndTagIdAndDepartedAtIsNull(UUID technicianId, UUID tagId);
    List<Presence> findByDepartedAtIsNull();
    /** Bornes de l'historique, pour construire la liste des années. */
    Optional<Presence> findFirstByOrderByArrivedAtAsc();
    Optional<Presence> findFirstByOrderByArrivedAtDesc();

    /** Les 8 dernières interventions, pour le tableau de bord. */
    List<Presence> findTop8ByOrderByArrivedAtDesc();

    long countByDepartedAtIsNull();

    long countByEstimatedTrue();

    long countByLocationStatus(LocationStatus status);

    /** Somme des durées en minutes, à l'identique de PresenceDurationCalculator. */
    @Query(value = "select coalesce(sum(floor(extract(epoch from (departed_at - arrived_at))/60)), 0)::bigint "
            + "from presence where departed_at is not null",
            nativeQuery = true)
    long sumDurationMinutes();

    long countByTagId(UUID tagId);
    long countByTechnicianId(UUID technicianId);
}
