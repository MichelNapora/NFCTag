export interface PresenceView {
  id: number;
  workerName: string;
  mobile: string;
  businessName: string;
  buildingName: string;
  wingName: string;
  tagName: string;
  arrivedAt: string;
  departedAt: string | null;
  durationMinutes: number | null;
  estimated: boolean;
  ongoing: boolean;
}

export interface StatRow {
  label: string;
  passages: number;
  totalMinutes: number;
}

export interface Stats {
  totalPassages: number;
  totalMinutes: number;
  ongoing: number;
  estimated: number;
  byBusiness: StatRow[];
  byBuilding: StatRow[];
}
