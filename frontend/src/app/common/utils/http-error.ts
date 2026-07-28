/** Extrait un message lisible d'une erreur HTTP (le back renvoie du texte ou une map champ→message). */
export function errorMessage(e: any, fallback: string): string {
  const body = e?.error;
  if (typeof body === 'string' && body) { return body; }
  if (body && typeof body === 'object') {
    const values = Object.values(body).filter(v => typeof v === 'string');
    if (values.length) { return values.join(' — '); }
  }
  return body?.message ?? fallback;
}
