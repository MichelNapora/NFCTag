package com.nfctag.scan.dto;

import com.nfctag.business.dto.BusinessDto;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Résultat d'un scan.
 * - status = RECOGNIZED          → scan traité (voir action)
 * - status = NEED_IDENTIFICATION → pas de jeton, demander le mobile
 * - status = NEED_BUSINESS       → mobile inconnu, demander la société
 */
public record ScanResult(
        String status,
        String action,          // ARRIVAL | DEPARTURE | null
        String deviceToken,     // nouveau jeton à stocker dans le navigateur (sinon null)
        TagInfo tag,
        String workerName,
        String businessName,
        OffsetDateTime time,
        List<BusinessDto> businesses // proposé seulement quand NEED_BUSINESS
) {
    public static final String RECOGNIZED = "RECOGNIZED";
    public static final String NEED_IDENTIFICATION = "NEED_IDENTIFICATION";
    public static final String NEED_BUSINESS = "NEED_BUSINESS";

    public static final String ACTION_ARRIVAL = "ARRIVAL";
    public static final String ACTION_DEPARTURE = "DEPARTURE";

    /** Réponse « scan traité » (arrivée ou départ). */
    public static ScanResult recognized(String action, String deviceToken, TagInfo tag,
                                        String workerName, String businessName, OffsetDateTime time) {
        return new ScanResult(RECOGNIZED, action, deviceToken, tag, workerName, businessName, time, null);
    }

    /** Réponse « il faut s'identifier par mobile ». */
    public static ScanResult needIdentification(TagInfo tag) {
        return new ScanResult(NEED_IDENTIFICATION, null, null, tag, null, null, null, null);
    }

    /** Réponse « mobile inconnu, choisir une société ». */
    public static ScanResult needBusiness(TagInfo tag, List<BusinessDto> businesses) {
        return new ScanResult(NEED_BUSINESS, null, null, tag, null, null, null, businesses);
    }
}
