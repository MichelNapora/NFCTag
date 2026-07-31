package com.nfctag.features.technician;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnicianRepository extends JpaRepository<Technician, UUID> {
    boolean existsByMobile(String mobile);
    boolean existsByMobileAndIdNot(String mobile, UUID id);
    List<Technician> findByBusinessId(UUID businessId);
    Optional<Technician> findByMobile(String mobile);
    Optional<Technician> findByDeviceToken(UUID deviceToken);
    long countByBusinessId(UUID businessId);
}
