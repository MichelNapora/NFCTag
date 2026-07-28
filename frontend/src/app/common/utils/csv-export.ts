/**
 * Génération de CSV lisibles par Excel en configuration française :
 * séparateur point-virgule et BOM UTF-8 (sans lui, les accents sont cassés).
 */

/** Construit le CSV et déclenche le téléchargement. */
export function downloadCsv(filename: string, header: string[], rows: string[][]): void {
  const csv = [header, ...rows]
    .map(line => line.map(csvCell).join(';'))
    .join('\r\n');

  const blob = new Blob(['\uFEFF' + csv], { type: 'text/csv;charset=utf-8;' });

  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = filename;
  a.click();
  setTimeout(() => URL.revokeObjectURL(url), 1000);
}

/** Date ISO → jj/mm/aaaa hh:mm. Chaîne vide si absente. */
export function csvDate(iso: string | null): string {
  if (!iso) { return ''; }
  const d = new Date(iso);
  const p2 = (n: number) => String(n).padStart(2, '0');
  return `${p2(d.getDate())}/${p2(d.getMonth() + 1)}/${d.getFullYear()} ${p2(d.getHours())}:${p2(d.getMinutes())}`;
}

/** Date du jour pour les noms de fichiers : 2026-07-28 */
export function csvToday(): string {
  return new Date().toISOString().substring(0, 10);
}

/** N'entoure de guillemets que si nécessaire, pour qu'Excel lise les nombres comme des nombres. */
function csvCell(value: string): string {
  const v = value ?? '';
  return /[";\r\n]/.test(v) ? `"${v.replace(/"/g, '""')}"` : v;
}
