export interface TagInfo {
  tagName: string;
  buildingName: string | null;
  wingName: string | null;
}

export interface BusinessDto {
  id: number;
  name: string;
}

export interface ScanResult {
  status: 'RECOGNIZED' | 'NEED_IDENTIFICATION' | 'NEED_BUSINESS';
  action: 'ARRIVAL' | 'DEPARTURE' | null;
  deviceToken: string | null;
  tag: TagInfo;
  workerName: string | null;
  businessName: string | null;
  time: string | null;
  businesses: BusinessDto[] | null;
}

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
