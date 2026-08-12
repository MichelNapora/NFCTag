# Déploiement de ScanProof

Application en production : **https://scanproof.spi.be**

## Où ça tourne

VM `spi-cloud46.spi.be` (139.165.54.11), Ubuntu 24.04, hébergée à l'ULiège.
**La machine héberge aussi ELES.** Ne pas toucher à sa configuration.

```
Internet ──443──> nginx ──┬── scanproof.spi.be ──> /var/www/scanproof (front)
                          │                   └──> 127.0.0.1:8081 (back ScanProof)
                          └── eles.spi.be ────────> 127.0.0.1:8080 (ELES)
```

Le port 8081 n'est pas ouvert vers l'extérieur : seul nginx lui parle.

## Emplacements

| Quoi | Où |
|---|---|
| Code source cloné depuis GitLab | `/opt/scanproof` |
| Configuration et mots de passe | `/opt/scanproof/backend/.env` |
| Application compilée | `/opt/scanproof/backend/target/nfctag-backend-0.0.1-SNAPSHOT.jar` |
| Front servi par nginx | `/var/www/scanproof` |
| Service systemd | `/etc/systemd/system/scanproof.service` |
| Bloc nginx | `/etc/nginx/sites-available/scanproof` |
| Certificat `*.spi.be` (partagé avec ELES) | `/etc/nginx/ssl/spi/` |

Base de données PostgreSQL locale : base `scanproof`, utilisateur `scanproof`.

## Mettre à jour l'application

```bash
cd /opt/scanproof
git pull

# Back
cd backend
mvn clean package -DskipTests
sudo systemctl restart scanproof

# Front
cd ../frontend
npm ci
npx ng build
cp -r dist/frontend/browser/* /var/www/scanproof/
```

Le front ne demande aucun redémarrage : nginx sert les fichiers directement.

## Commandes courantes

```bash
sudo systemctl status scanproof
sudo systemctl restart scanproof
journalctl -u scanproof -n 50 --no-pager
journalctl -u scanproof -f

sudo nginx -t && sudo systemctl reload nginx
```

## Vérifier que tout répond

```bash
curl -s -o /dev/null -w "scanproof : %{http_code}\n" https://scanproof.spi.be
curl -s -o /dev/null -w "API       : %{http_code}\n" https://scanproof.spi.be/api/auth/me
curl -s -o /dev/null -w "ELES      : %{http_code}\n" https://eles.spi.be
```

Attendu : **200**, **401**, **200**.
Le 401 est normal : l'API refuse l'accès tant qu'on n'est pas connecté.

## Diagnostic

**L'application ne démarre pas** — `journalctl -u scanproof -n 50`. Causes fréquentes : `.env` absent ou mal formé, base injoignable, port 8081 déjà pris.

**Erreur 502 dans le navigateur** — le back est arrêté. `sudo systemctl status scanproof`.

**Erreur 404 sur une adresse `/scan/...`** — le `try_files` du bloc nginx a disparu.

**Erreur au démarrage sur une colonne manquante** — le schéma de la base ne correspond pas aux migrations. Voir `backend/src/main/resources/db/migration/`.

**La géolocalisation ne fonctionne pas sur un téléphone** — vérifier le cadenas dans la barre d'adresse. Sans HTTPS valide, le navigateur refuse la position.

## Points de vigilance

**Le certificat `*.spi.be` expire le 9 octobre 2026.** Il est partagé avec ELES et ne se renouvelle pas automatiquement. Après remplacement des fichiers dans `/etc/nginx/ssl/spi/`, faire `sudo systemctl reload nginx`.

**Espace disque limité.** Environ 2 Go libres sur 10, partagés avec ELES.

**Ne pas redémarrer la VM sans prévenir**, ELES tourne dessus.

**`max-accuracy-meters` est réglé à 250** dans `backend/src/main/resources/application.yml`. C'est le seuil au-delà duquel une position est jugée trop imprécise pour être fiable. À réviser avec des mesures GPS faites sur le terrain.

## Reconstruire depuis zéro

```bash
sudo apt install -y openjdk-21-jdk-headless maven nginx
sudo -u postgres psql -c "create user scanproof with password 'MOT_DE_PASSE';"
sudo -u postgres psql -c "create database scanproof owner scanproof;"
sudo mkdir -p /opt/scanproof /var/www/scanproof
sudo chown $USER:$USER /opt/scanproof /var/www/scanproof
cd /opt/scanproof && git clone https://gitlab.com/ICT.spi/scanproof.git .
cd backend && mvn clean package -DskipTests
cd ../frontend && npm ci && npx ng build
cp -r dist/frontend/browser/* /var/www/scanproof/
```

Puis recréer les trois fichiers de configuration.

### `/opt/scanproof/backend/.env` — droits 600

```properties
DB_URL=jdbc:postgresql://localhost:5432/scanproof
DB_USER=scanproof
DB_PASSWORD=MOT_DE_PASSE

MAX_LOGIN_ATTEMPTS=3

ADMIN_EMAIL=admin.spi@spi.be
ADMIN_PASSWORD=MOT_DE_PASSE_ADMIN

COOKIE_SECURE=true
PUBLIC_BASE_URL=https://scanproof.spi.be

# ELES occupe deja le port 8080
server.port=8081
```

### `/etc/systemd/system/scanproof.service`

```ini
[Unit]
Description=ScanProof - suivi des interventions
After=network.target postgresql.service

[Service]
Type=simple
User=gestion
WorkingDirectory=/opt/scanproof/backend
ExecStart=/usr/bin/java -jar /opt/scanproof/backend/target/nfctag-backend-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

### `/etc/nginx/sites-available/scanproof`

```nginx
server {
    listen 443 ssl http2;
    server_name scanproof.spi.be;

    ssl_certificate     /etc/nginx/ssl/spi/fullchain.pem;
    ssl_certificate_key /etc/nginx/ssl/spi/privkey.pem;
    ssl_protocols TLSv1.2 TLSv1.3;

    root /var/www/scanproof;
    index index.html;

    location /api/ {
        proxy_pass http://127.0.0.1:8081/;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location / {
        try_files $uri $uri/ /index.html;
    }
}
```

Activer et démarrer :

```bash
sudo ln -s /etc/nginx/sites-available/scanproof /etc/nginx/sites-enabled/scanproof
sudo nginx -t && sudo systemctl reload nginx
sudo systemctl daemon-reload
sudo systemctl enable --now scanproof
```

Flyway crée les tables au premier démarrage, et le compte administrateur est créé à partir du `.env`.
