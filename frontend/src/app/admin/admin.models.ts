export interface BusinessAdmin {
  id: number;
  name: string;
  bce: string | null;
}

export interface BuildingAdmin {
  id: number;
  name: string;
  projectCode: string | null;
  buildingType: string | null;
  city: string | null;
}

export interface WingAdmin {
  id: number;
  name: string;
  buildingId: number;
  buildingName: string;
}

export interface TagAdmin {
  id: number;
  name: string;
  wingId: number;
  wingName: string;
  buildingName: string;
  scanToken: string;
  url: string;
}
