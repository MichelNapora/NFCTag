\# Déploiement de ScanProof



\## Où ça tourne



VM `spi-cloud46.spi.be` (139.165.54.11), Ubuntu 24.04, hébergée à l'ULiège.

\*\*La machine héberge aussi ELES.\*\* Ne pas toucher à sa configuration.



```

Internet ──443──> nginx ──┬── spi-cloud46.spi.be ──> /var/www/scanproof (front)

&#x20;                         │                     └──> 127.0.0.1:8081 (back ScanProof)

&#x20;                         └── eles.spi.be ──────────> 127.0.0.1:8080 (ELES)

```



Le port 8081 n'est pas ouvert vers l'extérieur : seul nginx lui parle.



\## Emplacements



| Quoi | Où |

|---|---|

| Code source cloné depuis GitLab | `/opt/scanproof` |

| Configuration et mots de passe | `/opt/scanproof/backend/.env` |

| Application compilée | `/opt/scanproof/backend/target/nfctag-backend-0.0.1-SNAPSHOT.jar` |

| Front servi par nginx | `/var/www/scanproof` |

| Service systemd | `/etc/systemd/system/scanproof.service` |

| Bloc nginx | `/etc/nginx/sites-available/scanproof` |

| Certificat `\*.spi.be` (partagé avec ELES) | `/etc/nginx/ssl/spi/` |



Base de données PostgreSQL locale : base `scanproof`, utilisateur `scanproof`.



\## Mettre à jour l'application



```bash

cd /opt/scanproof

git pull



\# Back

cd backend

mvn clean package -DskipTests

sudo systemctl restart scanproof



\# Front

cd ../frontend

npm ci

npx ng build

cp -r dist/frontend/browser/\* /var/www/scanproof/

```



Le front ne demande aucun redémarrage : nginx sert les fichiers directement.



\## Commandes courantes



```bash

sudo systemctl status scanproof

sudo systemctl restart scanproof

journalctl -u scanproof -n 50 --no-pager

journalctl -u scanproof -f



sudo nginx -t \&\& sudo systemctl reload nginx

```



\## Diagnostic



\*\*L'application ne démarre pas\*\* — `journalctl -u scanproof -n 50`. Causes fréquentes : `.env` absent ou mal formé, base injoignable, port 8081 déjà pris.



\*\*Erreur 502 dans le navigateur\*\* — le back est arrêté. `sudo systemctl status scanproof`.



\*\*Erreur 404 sur une adresse `/scan/...`\*\* — le `try\_files` du bloc nginx a disparu.



\*\*Erreur au démarrage sur une colonne manquante\*\* — le schéma de la base ne correspond pas aux migrations. Voir `backend/src/main/resources/db/migration/`.



\## Points de vigilance



\*\*Le certificat `\*.spi.be` expire le 9 octobre 2026.\*\* Il est partagé avec ELES et ne se renouvelle pas automatiquement. Après remplacement des fichiers dans `/etc/nginx/ssl/spi/`, faire `sudo systemctl reload nginx`.



\*\*Bascule vers `scanproof.spi.be`\*\* quand l'entrée DNS existera. Deux lignes à changer :

\- `server\_name` dans `/etc/nginx/sites-available/scanproof`

\- `PUBLIC\_BASE\_URL` dans `/opt/scanproof/backend/.env`



Puis `sudo nginx -t \&\& sudo systemctl reload nginx` et `sudo systemctl restart scanproof`.

Les QR codes suivent automatiquement : ils se construisent à partir de l'adresse du navigateur.



\*\*Espace disque limité.\*\* Environ 2 Go libres sur 10, partagés avec ELES.



\*\*Ne pas redémarrer la VM sans prévenir\*\*, ELES tourne dessus.



\## Reconstruire depuis zéro



```bash

sudo apt install -y openjdk-21-jdk-headless maven nginx

sudo -u postgres psql -c "create user scanproof with password 'MOT\_DE\_PASSE';"

sudo -u postgres psql -c "create database scanproof owner scanproof;"

sudo mkdir -p /opt/scanproof /var/www/scanproof

sudo chown $USER:$USER /opt/scanproof /var/www/scanproof

cd /opt/scanproof \&\& git clone https://gitlab.com/ICT.spi/scanproof.git .

```



Puis recréer `.env`, le service systemd et le bloc nginx tels que décrits ci-dessus.

Flyway crée les tables au premier démarrage.

