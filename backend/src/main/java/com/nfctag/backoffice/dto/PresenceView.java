package com.nfctag.backoffice.dto;

import java.time.OffsetDateTime;

/** Une intervention (présence) enrichie et calculée. */
public record PresenceView(
        Long id,
        String workerName,
        String mobile,
        String businessName,
        String buildingName,
        String wingName,
        String tagName,
        OffsetDateTime arrivedAt,
        OffsetDateTime departedAt,
        Long durationMinutes,
        boolean estimated,
        boolean ongoing) {
}
