package com.nfctag.repository;

import com.nfctag.domain.WorkerDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkerDeviceRepository extends JpaRepository<WorkerDevice, Long> {
    Optional<WorkerDevice> findByToken(UUID token);
}
