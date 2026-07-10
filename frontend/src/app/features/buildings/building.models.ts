/** AddressDTO du back. */
export interface Address {
  id?: string;
  street: string;
  number: number;
  box: string | null;
  postalCode: number;
  city: string;
}

/** BuildingDTO du back (adresse imbriquée). */
export interface Building {
  id: string;
  name: string;
  projectCode: string;
  address: Address;
}
