package com.nfctag.repository;

import com.nfctag.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findByMobile(String mobile);
}
