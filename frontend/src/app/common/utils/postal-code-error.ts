import { POSTAL_CODE_NOT_INTEGER, POSTAL_CODE_OUT_OF_ZONE } from '../messages';

/** Vérifie le code postal, comme le back (@Min 4000, @Max 4999 : la province de Liège). Renvoie le message, ou null. */
export function postalCodeError(value: number | null): string | null {
  if (value == null) { return null; }
  if (!Number.isInteger(value)) { return POSTAL_CODE_NOT_INTEGER; }
  return value >= 4000 && value <= 4999 ? null : POSTAL_CODE_OUT_OF_ZONE;
}
