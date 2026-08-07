import { INVALID_PROJECT_CODE } from '../messages';

/** Vérifie le code projet, comme le back (@Size 4 à 8). Renvoie le message, ou null si tout va bien. */
export function projectCodeError(value: string): string | null {
  const v = value.trim();
  if (!v) { return null; }
  return v.length >= 4 && v.length <= 8 ? null : INVALID_PROJECT_CODE;
}
