package com.nfctag.building;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByArchivedIsNullOrderByName();
}
