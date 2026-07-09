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
}

/** Une ligne de répartition (calculée côté front). */
export interface StatRow {
  label: string;
  passages: number;
  totalMinutes: number;
}

/** Synthèse calculée côté front à partir des présences. */
export interface Stats {
  totalPassages: number;
  totalMinutes: number;
  ongoing: number;
  estimated: number;
  byBusiness: StatRow[];
  byBuilding: StatRow[];
}
