/** TagDTO du back. L'URL à encoder sur le tag physique se construit avec scanToken. */
export interface Tag {
  id: string;
  scanToken: string;
  latitude: number | null;
  longitude: number | null;
  wingId: string;
  calibratedAt: string | null;
}
