import { LocationStatus } from '../../common/utils/location-status';

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
}
