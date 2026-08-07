import { INVALID_NAME, NAME_TOO_SHORT, NAME_TOO_LONG } from '../messages';

/** Vérifie un nom ou un prénom, comme le back (@ValidName + @Size 2 à 50). Renvoie le message, ou null. */
export function nameError(value: string): string | null {
  const v = value.trim();
  if (!v) { return null; }
  if (v.length < 2) { return NAME_TOO_SHORT; }
  if (v.length > 50) { return NAME_TOO_LONG; }
  // Des groupes de lettres, séparés par un seul espace, trait d'union ou apostrophe.
  return /^\p{L}+([ '\-]\p{L}+)*$/u.test(v) ? null : INVALID_NAME;
}
