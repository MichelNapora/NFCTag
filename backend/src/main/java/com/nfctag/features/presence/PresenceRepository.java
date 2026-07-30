package com.nfctag.features.presence;

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

}
