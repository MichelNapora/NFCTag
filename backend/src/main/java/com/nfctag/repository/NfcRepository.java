package com.nfctag.repository;

import com.nfctag.domain.Nfc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NfcRepository extends JpaRepository<Nfc, Long> {
    Optional<Nfc> findByScanToken(UUID scanToken);
}
