import { LocationModel } from '../location/location.model';

/** Ce que le front envoie au back lors d'un scan (ScanRequestDTO). */
export interface ScanRequest {
  deviceToken: string | null;   // null au 1er scan sur ce navigateur
  latitude: number | null;      // null si GPS refusé
  longitude: number | null;
  accuracy: number | null;      // précision GPS en mètres
  // utilisés seulement au 1er passage (deviceToken null)
  firstname: string | null;
  lastname: string | null;
  mobile: string | null;
  businessId: string | null;
}

/** Ce que le back renvoie après un scan (ScanResponseDTO). */
export interface ScanResponse {
  deviceToken: string;          // à stocker dans le navigateur
  technicianName: string;
  buildingName: string;
  wingName: string;
  arrivedAt: string;
  departedAt: string | null;
  locationVerified: boolean;
  locationStatus: LocationModel;
  distanceMeters: number | null;
  action: 'ARRIVAL' | 'DEPARTURE';
}

/** Une société (BusinessDTO) pour la liste du 1er passage. */
export interface Business {
  id: string;
  name: string;
  bce: string;
}

/** Position envoyée lors de la calibration d'un tag par un employé. */
export interface TagPosition {
  latitude: number;
  longitude: number;
  accuracy: number;
}

/** Tag calibré renvoyé par le back (TagDTO). */
export interface CalibratedTag {
  id: string;
  scanToken: string;
  latitude: number | null;
  longitude: number | null;
  calibratedAt: string | null;
  wingId: string;
}
