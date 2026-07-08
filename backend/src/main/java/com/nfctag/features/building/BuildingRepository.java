package com.nfctag.features.building;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuildingRepository extends JpaRepository<Building, UUID> {
    boolean existsByProjectCode(String projectCode);
    boolean existsByProjectCodeAndIdNot(String projectCode, UUID id);
}
