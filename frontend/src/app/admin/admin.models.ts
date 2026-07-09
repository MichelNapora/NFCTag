/** AddressDTO du back. */
export interface AddressAdmin {
  id?: string;
  street: string;
  number: number;
  box: string | null;
  postalCode: number;
  city: string;
}

/** BusinessDTO du back. */
export interface BusinessAdmin {
  id: string;
  name: string;
  bce: string;
}

/** BuildingDTO du back (adresse imbriquée). */
export interface BuildingAdmin {
  id: string;
  name: string;
  projectCode: string;
  address: AddressAdmin;
}

/** WingDTO du back (id du bâtiment seulement — le nom se retrouve côté front). */
export interface WingAdmin {
  id: string;
  name: string;
  buildingId: string;
}

/** TagDTO du back. L'URL à encoder sur le tag se construit avec le scanToken. */
export interface TagAdmin {
  id: string;
  scanToken: string;
  latitude: number | null;
  longitude: number | null;
  wingId: string;
}
