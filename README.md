# NFCTag — Suivi des interventions par tags NFC

Application de pointage pour ouvriers/techniciens externes intervenant sur des
bâtiments. Chaque tag NFC (un par aile) contient l'URL du site ; le technicien
scanne **en arrivant** (heure d'arrivée) et **en repartant** (heure de départ).
Les collègues consultent un back-office qui récapitule le nombre de passages et
la durée des interventions.

## Stack

- **Base de données** : PostgreSQL 16 (migrations Flyway)
- **Backend** : Spring Boot 3 / Java 21 (API REST)
- **Frontend** : Angular 18 (page de scan mobile + tableau de bord)

## Principes métier

- **Identification du technicien** : au 1ᵉʳ passage il saisit son mobile et
  choisit sa **société**. Un **jeton** est déposé dans son navigateur : aux
  passages suivants (même longtemps après) il est reconnu automatiquement.
  S'il a changé d'appareil / vidé ses cookies, il ressaisit son mobile et on le
  retrouve en base (sans redemander la société).
  > Note : un navigateur ne peut pas lire le n° de téléphone automatiquement
  > (bloqué par iOS/Android), d'où le jeton + repli mobile.
- **Arrivée / départ par bascule** : un scan sans présence ouverte = arrivée ;
  le scan suivant sur le même tag = départ.
- **Oubli de scan de sortie** : un job ferme les présences ouvertes à
  `arrivée + 1h` (durée estimée, configurable).

## Modèle de données

`business` (société) ─< `worker` (technicien) ─< `presence` (intervention) >─ `nfc` (tag)
`address` ─< `building` ─< `wing` ─ `nfc`. Une table `worker_device` stocke les
jetons de reconnaissance navigateur. Voir `backend/src/main/resources/db/migration`.

## Organisation du code (par feature)

Le code est découpé **par fonctionnalité** (package-by-feature), pas par couche
technique. Chaque feature regroupe tout ce qui la concerne.

**Backend** (`com.nfctag.*`) :
```
business/  worker/  address/  building/  wing/  nfc/  presence/
scan/        → flux de scan (controller, service, dto/)
backoffice/  → consultation (controller, service, dto/)
config/      → technique transverse (propriétés, CORS)
```

**Frontend** (`src/app/*`) :
```
scan/        → scan.component, scan.service, scan.models
backoffice/  → dashboard.component, backoffice.service, backoffice.models
```

## Lancer en local (sans Docker)

### 1. Base PostgreSQL
PostgreSQL doit être installé et démarré. Créez la base et l'utilisateur :
```sql
CREATE USER nfctag WITH PASSWORD 'nfctag';
CREATE DATABASE nfctag OWNER nfctag;
```
> Paramétrable via les variables d'env `DB_URL`, `DB_USER`, `DB_PASSWORD`.

### 2. Backend (port 8080)
```bash
cd backend
mvn spring-boot:run
```
Flyway crée le schéma et insère des données de démo au démarrage.

### 3. Frontend (port 4200)
```bash
cd frontend
npm install
npm start        # ng serve, proxy /api -> http://localhost:8080
```

### Accès
- **Tableau de bord** : http://localhost:4200/dashboard
- **Page de scan** (simulée) : http://localhost:4200/scan/<scan_token>
  Tags de démo : `11111111-1111-1111-1111-111111111111` (Aile Nord),
  `22222222-…` (Aile Sud), `33333333-…` (Bloc A).

L'URL inscrite sur un vrai tag NFC est donc `https://<domaine>/scan/<scan_token>`.

## API (extrait)

| Méthode | Endpoint                     | Rôle                                   |
|---------|------------------------------|----------------------------------------|
| POST    | `/api/scan`                  | Scan (jeton appareil si présent)       |
| POST    | `/api/scan/lookup`           | Repli : identification par mobile      |
| POST    | `/api/scan/register`         | 1ᵉʳ passage : création + choix société |
| GET     | `/api/backoffice/presences`  | Liste des interventions                |
| GET     | `/api/backoffice/stats`      | Statistiques (passages, durées)        |

## À venir

- **Authentification Windows** du back-office (Active Directory interne ou
  Azure AD / Entra ID — à déterminer). Volontairement non branchée pour
  l'instant : l'accès au back-office est ouvert en développement.
- Administration des bâtiments / ailes / tags depuis l'interface.
