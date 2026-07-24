// ============================================================
// Sert la version compilée d'Angular (dist/) + relaie /api vers le backend.
// Aucune dépendance : uniquement Node.  Lancer avec :  node serve-prod.mjs
// ============================================================
import http from 'node:http';
import { readFile, stat } from 'node:fs/promises';
import { join, extname, normalize } from 'node:path';
import { fileURLToPath } from 'node:url';

const PORT = 4200;                 // le port que ngrok redirige déjà
const BACKEND = { host: '127.0.0.1', port: 8080 };
const ROOT = join(fileURLToPath(new URL('.', import.meta.url)), 'dist', 'frontend', 'browser');

const MIME = {
  '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8',
  '.css': 'text/css; charset=utf-8', '.svg': 'image/svg+xml', '.ico': 'image/x-icon',
  '.json': 'application/json; charset=utf-8', '.woff': 'font/woff', '.woff2': 'font/woff2',
  '.ttf': 'font/ttf', '.png': 'image/png', '.jpg': 'image/jpeg', '.jpeg': 'image/jpeg', '.webp': 'image/webp'
};

const server = http.createServer(async (req, res) => {
  const url = new URL(req.url, 'http://localhost');
  const path = decodeURIComponent(url.pathname);

  // 1) Appels API -> proxy vers le backend Spring (on enlève le préfixe /api)
  if (path.startsWith('/api/') || path === '/api') {
    const backendPath = req.url.replace(/^\/api/, '') || '/';
    const proxyReq = http.request({
      host: BACKEND.host, port: BACKEND.port, method: req.method,
      path: backendPath, headers: { ...req.headers, host: `${BACKEND.host}:${BACKEND.port}` }
    }, (proxyRes) => { res.writeHead(proxyRes.statusCode || 502, proxyRes.headers); proxyRes.pipe(res); });
    proxyReq.on('error', () => { res.writeHead(502, { 'content-type': 'text/plain' });
      res.end('Backend indisponible (le serveur Spring tourne-t-il sur le port 8080 ?)'); });
    req.pipe(proxyReq);
    return;
  }

  // 2) Fichier statique s'il existe
  const safe = normalize(path).replace(/^(\.\.[/\\])+/, '');
  let filePath = join(ROOT, safe);
  try {
    const info = await stat(filePath);
    if (info.isDirectory()) { filePath = join(filePath, 'index.html'); }
    const data = await readFile(filePath);
    res.writeHead(200, { 'content-type': MIME[extname(filePath)] || 'application/octet-stream' });
    res.end(data);
    return;
  } catch { /* pas un fichier -> fallback SPA */ }

  // 3) Route Angular -> index.html (le routeur prend le relais)
  try {
    const index = await readFile(join(ROOT, 'index.html'));
    res.writeHead(200, { 'content-type': 'text/html; charset=utf-8' });
    res.end(index);
  } catch {
    res.writeHead(500, { 'content-type': 'text/plain' });
    res.end('Build introuvable. Lance d\'abord :  npx ng build');
  }
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`\n  App servie sur  http://localhost:${PORT}`);
  console.log(`  API relayée    /api/*  ->  http://${BACKEND.host}:${BACKEND.port}`);
  console.log(`  (Ctrl+C pour arrêter)\n`);
});
