package com.nfctag.wing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WingRepository extends JpaRepository<Wing, Long> {
    List<Wing> findByArchivedIsNullOrderByName();
}
