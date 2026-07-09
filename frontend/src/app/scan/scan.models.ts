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
  action: 'ARRIVAL' | 'DEPARTURE';
}

/** Une société (BusinessDTO) pour la liste du 1er passage. */
export interface Business {
  id: string;
  name: string;
  bce: string;
}
