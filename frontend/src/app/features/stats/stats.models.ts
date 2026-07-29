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
