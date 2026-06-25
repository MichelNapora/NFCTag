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
