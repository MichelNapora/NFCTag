package com.nfctag.features.location;

public enum LocationStatus {
    /** GPS fiable et à portée du tag : passage confirmé. */
    VERIFIED,
    /** GPS fiable mais loin du tag : anomalie à contrôler. */
    TOO_FAR,
    /** Localisation refusée ou indisponible sur le téléphone. */
    NO_GPS,
    /** GPS trop imprécis pour conclure (fréquent en intérieur). */
    IMPRECISE,
    /** Le tag n'a pas encore de position enregistrée. */
    TAG_NOT_CALIBRATED
}
