export interface Address {
  id?: string;
  street: string;
  number: number;
  box: string | null;
  postalCode: number;
  city: string;
}

export interface Building {
  id: string;
  name: string;
  projectCode: string;
  address: Address;
}
