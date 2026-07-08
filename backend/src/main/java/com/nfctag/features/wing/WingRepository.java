package com.nfctag.features.wing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WingRepository extends JpaRepository<Wing, UUID> {
    List<Wing> findByBuildingId(UUID buildingId);
    boolean existsByNameAndBuildingId(String name, UUID buildingId);
    boolean existsByNameAndBuildingIdAndIdNot(String name, UUID buildingId, UUID id);
}
