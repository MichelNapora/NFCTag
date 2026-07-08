package com.nfctag.features.business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessRepository extends JpaRepository<Business, UUID> {
    boolean existsByBce(String bce);
    boolean existsByBceAndIdNot(String bc,UUID id);
}
