/**
 * 0494793040 → 0494 79 30 40.
 * Les espaces empêchent Excel de lire le mobile comme un nombre et d'en perdre le zéro initial.
 */
export function mobileDisplay(mobile: string): string {
  const chiffres = mobile.replace(/\D/g, '');
  if (chiffres.length !== 10) { return mobile; }
  return `${chiffres.substring(0, 4)} ${chiffres.substring(4, 6)} ${chiffres.substring(6, 8)} ${chiffres.substring(8)}`;
}
