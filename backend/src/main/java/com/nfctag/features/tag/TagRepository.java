package com.nfctag.features.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag,UUID> {
    Optional<Tag>findByScanToken(UUID scanToken);
    boolean existsByWingId(UUID wingId);
    boolean existsByWingIdAndIdNot(UUID wingId, UUID id);
    List<Tag> findByWingBuildingId(UUID buildingId);
}
