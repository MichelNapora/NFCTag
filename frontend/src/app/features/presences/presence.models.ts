import { LocationStatus } from '../location/location.models';

/** Une intervention (PresenceDTO du back). */
export interface PresenceView {
  id: string;
  technicianName: string;
  mobile: string;
  businessName: string;
  buildingName: string;
  wingName: string;
  arrivedAt: string;
  departedAt: string | null;
  durationMinutes: number | null;
  estimated: boolean;
  locationVerified: boolean;
  locationStatus: LocationStatus | null;
  distanceMeters: number | null;
  departureLocationStatus: LocationStatus | null;
  departureDistanceMeters: number | null;
}

/** Métadonnées de pagination renvoyées par Spring (PagedModel). */
export interface PageMeta {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
}

/** Une page d'interventions. */
export interface PresencePage {
  content: PresenceView[];
  page: PageMeta;
}

/** Compteurs des pastilles et années disponibles (SearchMetaDTO du back). */
export interface SearchMeta {
  years: number[];
  all: number;
  ongoing: number;
  done: number;
  estimated: number;
  suspect: number;
}

