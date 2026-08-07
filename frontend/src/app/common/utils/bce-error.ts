import { INVALID_BCE_CHECK, INVALID_BCE_FORMAT } from '../messages';

/** Vérifie le numéro BCE, comme le back (10 chiffres, début 0/1, clé de contrôle). Renvoie le message, ou null. */
export function bceError(value: string): string | null {
  const v = value.trim();
  if (!v) { return null; }
  if (!/^[01]\d{9}$/.test(v)) { return INVALID_BCE_FORMAT; }
  // La clé de contrôle : les 2 derniers chiffres valent 97 moins le reste des 8 premiers.
  const base = Number(v.substring(0, 8));
  const cle = Number(v.substring(8));
  return 97 - (base % 97) === cle ? null : INVALID_BCE_CHECK;
}
