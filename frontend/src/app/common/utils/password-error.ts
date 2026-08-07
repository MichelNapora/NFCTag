import { PASSWORD_TOO_SHORT, PASSWORD_TOO_LONG } from '../messages';

/** Vérifie le mot de passe, comme le back (@Size 8 à 72). Vide = inchangé en modification, donc rien à signaler. */
export function passwordError(value: string): string | null {
  if (!value) { return null; }
  if (value.length < 8) { return PASSWORD_TOO_SHORT; }
  // BCrypt ignore tout ce qui dépasse 72 octets : au-delà, deux mots de passe différents se vaudraient.
  return value.length <= 72 ? null : PASSWORD_TOO_LONG;
}
