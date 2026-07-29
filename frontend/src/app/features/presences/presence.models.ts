import { LocationModel } from '../location/location.model';

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
  locationStatus: LocationModel | null;
  distanceMeters: number | null;
}

/** Fiabilité de localisation par technicien (TechnicianStatsDTO du back). */
export interface TechnicianStats {
  technicianId: string;
  technicianName: string;
  businessName: string;
  totalScans: number;
  locatedScans: number;
  tooFarScans: number;
  locatedRate: number | null;
}
