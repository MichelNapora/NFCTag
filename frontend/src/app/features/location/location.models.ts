import {
  LOCATION_IMPRECISE, LOCATION_NO_GPS, LOCATION_TAG_NOT_CALIBRATED,
  LOCATION_TOO_FAR, LOCATION_VERIFIED, NO_LOCATION
} from '../../common/messages';

/** Statut de localisation d'un scan (LocationStatus du back). */
export type LocationStatus =
  | 'VERIFIED'
  | 'TOO_FAR'
  | 'NO_GPS'
  | 'IMPRECISE'
  | 'TAG_NOT_CALIBRATED';

/** Libellé français par statut, pour le back-office. */
export const LOCATION_LABEL: Record<LocationStatus, string> = {
  VERIFIED: LOCATION_VERIFIED,
  TOO_FAR: LOCATION_TOO_FAR,
  NO_GPS: LOCATION_NO_GPS,
  IMPRECISE: LOCATION_IMPRECISE,
  TAG_NOT_CALIBRATED: LOCATION_TAG_NOT_CALIBRATED
};


/** Libellé d'un statut, ou « Non renseigné » quand le scan n'en a pas. */
export function locationLabelOf(status: LocationStatus | null): string {
  return status ? LOCATION_LABEL[status] : NO_LOCATION;
}
