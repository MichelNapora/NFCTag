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

/** Fiabilité de localisation par société (BusinessStatsDTO du back). */
export interface BusinessStats {
  businessId: string;
  businessName: string;
  technicianCount: number;
  totalScans: number;
  locatedScans: number;
  tooFarScans: number;
  locatedRate: number | null;
}
