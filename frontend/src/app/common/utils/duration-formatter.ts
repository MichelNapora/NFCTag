/** Formate une durée en minutes : « 2h15 » ou « 45 min », « — » si inconnue. */
export function formatDuration(minutes: number | null): string {
  if (minutes == null) { return '—'; }
  const h = Math.floor(minutes / 60);
  const m = minutes % 60;
  return h > 0 ? `${h}h${m.toString().padStart(2, '0')}` : `${m} min`;
}
