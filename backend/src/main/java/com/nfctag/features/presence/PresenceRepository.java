package com.nfctag.features.presence;

import com.nfctag.features.location.LocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
    Optional<Presence> findFirstByOrderByArrivedAtAsc();
    Optional<Presence> findFirstByOrderByArrivedAtDesc();
    List<Presence> findTop8ByOrderByArrivedAtDesc();
    long countByDepartedAtIsNull();
    long countByEstimatedTrue();
    long countByTagId(UUID tagId);
    long countByTechnicianId(UUID technicianId);
    boolean existsByTagId(UUID tagId);
    boolean existsByTechnicianId(UUID technicianId);
    List<Presence> findByTechnicianIdAndDepartedAtIsNull(UUID technicianId);
}
