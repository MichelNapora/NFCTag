import { NUMBER_NOT_INTEGER, NUMBER_NOT_POSITIVE } from '../messages';

/** Vérifie le numéro de rue, comme le back (@Positive). Renvoie le message, ou null. */
export function streetNumberError(value: number | null): string | null {
  if (value == null) { return null; }
  if (!Number.isInteger(value)) { return NUMBER_NOT_INTEGER; }
  return value > 0 ? null : NUMBER_NOT_POSITIVE;
}
