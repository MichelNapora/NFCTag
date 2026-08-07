/** Remplit les %s d'un message dans l'ordre  */
export function format(modele: string, ...valeurs: string[]): string {
  let i = 0;
  return modele.replace(/%s/g, () => valeurs[i++] ?? '');
}
