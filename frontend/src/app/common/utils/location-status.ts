/** Statut de localisation d'un scan (LocationStatus du back). */
export type LocationStatus =
  | 'VERIFIED'
  | 'TOO_FAR'
  | 'NO_GPS'
  | 'IMPRECISE'
  | 'TAG_NOT_CALIBRATED';

/** Libellé français par statut, pour le back-office. */
export const LOCATION_LABEL: Record<LocationStatus, string> = {
  VERIFIED: 'Position vérifiée',
  TOO_FAR: 'Trop loin du tag',
  NO_GPS: 'Sans GPS',
  IMPRECISE: 'GPS imprécis',
  TAG_NOT_CALIBRATED: 'Tag non calibré'
};
