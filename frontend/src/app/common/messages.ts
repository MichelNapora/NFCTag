// ---------- formats de saisie ----------
export const INVALID_NAME            = 'Lettres, espaces, traits d\'union et apostrophes uniquement.';
export const NAME_TOO_SHORT          = 'Le nom doit faire au moins 2 caractères.';
export const NAME_TOO_LONG           = 'Le nom ne peut pas dépasser 50 caractères.';
export const INVALID_MOBILE          = 'Le mobile doit faire 10 chiffres et commencer par 04. Ex : 0470 11 12 22';
export const INVALID_PROJECT_CODE    = 'Le code projet doit faire entre 4 et 8 caractères.';
export const INVALID_BCE_FORMAT      = 'Le numéro BCE doit faire 10 chiffres et commencer par 0 ou 1.';
export const INVALID_BCE_CHECK       = 'La clé de contrôle du numéro BCE est incorrecte.';
export const POSTAL_CODE_NOT_INTEGER = 'Le code postal doit être un nombre entier.';
export const POSTAL_CODE_OUT_OF_ZONE = 'Le code postal doit être compris entre 4000 et 4999.';
export const NUMBER_NOT_INTEGER      = 'Le numéro doit être un nombre entier.';
export const NUMBER_NOT_POSITIVE     = 'Le numéro doit être supérieur à zéro.';
export const PASSWORD_TOO_SHORT      = 'Le mot de passe doit faire au moins 8 caractères.';
export const PASSWORD_TOO_LONG       = 'Le mot de passe ne peut pas dépasser 72 caractères.';

// ---------- erreurs ----------
export const GENERIC_ERROR          = 'Une erreur est survenue.';
export const ACTION_FAILED          = 'Action impossible.';
export const EXPORT_FAILED          = 'Export impossible.';
export const PASSWORD_CHANGE_FAILED = 'Changement impossible.';
export const LOGIN_FAILED           = 'Connexion impossible. Réessayez.';
export const NETWORK_ERROR      = 'Serveur injoignable. Vérifiez votre connexion.';
export const INVALID_DATA       = 'Données invalides. Vérifiez votre saisie.';
export const NOT_FOUND_ERROR    = 'Élément introuvable. Il a peut-être été supprimé.';
export const ALREADY_EXISTS     = 'Cette donnée existe déjà.';
export const DELETE_BLOCKED     = 'Suppression impossible : cet élément est utilisé ailleurs.';
export const TOO_MANY_REQUESTS  = 'Trop de tentatives. Patientez un instant.';

// ---------- confirmations : titres ----------
export const TITLE_DELETE_BUILDING   = 'Supprimer le bâtiment';
export const TITLE_DELETE_WING       = 'Supprimer l\'aile';
export const TITLE_DELETE_TAG        = 'Supprimer le tag';
export const TITLE_DELETE_BUSINESS   = 'Supprimer la société';
export const TITLE_DELETE_TECHNICIAN = 'Supprimer le technicien';
export const TITLE_DELETE_PRESENCE   = 'Supprimer l\'intervention';
export const TITLE_DELETE_EMPLOYEE   = 'Supprimer le compte';
export const TITLE_UNLOCK_EMPLOYEE   = 'Déverrouiller le compte';
export const TITLE_RECALIBRATE_TAG   = 'Recalibrer le tag';
export const TITLE_LOGOUT            = 'Se déconnecter';

// ---------- confirmations : questions ----------
export const CONFIRM_DELETE            = 'Voulez-vous supprimer « %s » ?';
export const CONFIRM_DELETE_TAG        = 'Voulez-vous supprimer le tag « %s » ?';
export const CONFIRM_DELETE_TECHNICIAN = 'Voulez-vous supprimer « %s » ? Ses interventions seront perdues.';
export const CONFIRM_DELETE_EMPLOYEE   = 'Voulez-vous supprimer le compte de « %s » ?';
export const CONFIRM_UNLOCK_EMPLOYEE   = 'Voulez-vous déverrouiller le compte de « %s » ?';
export const CONFIRM_DELETE_PRESENCE   = 'Intervention de %s du %s. Cette suppression est définitive.';
export const CONFIRM_RECALIBRATE       = 'La position du tag « %s » sera effacée, puis réenregistrée en scannant le tag sur place avec un téléphone connecté.';
export const CONFIRM_LOGOUT            = 'Voulez-vous continuer ?';

// ---------- confirmations : boutons ----------
export const BUTTON_DELETE      = 'Supprimer';
export const BUTTON_UNLOCK      = 'Déverrouiller';
export const BUTTON_RECALIBRATE = 'Recalibrer';
export const BUTTON_LOGOUT      = 'Se déconnecter';
export const BUTTON_CONFIRM     = 'Confirmer';

// ---------- textes des gabarits ----------
export const CANCEL          = 'Annuler';
export const CLOSE           = 'Fermer';
export const PASSWORD_SHOW   = 'Afficher le mot de passe';
export const PASSWORD_HIDE   = 'Masquer le mot de passe';

export const LOGIN_TITLE      = 'Suivi des interventions';
export const LOGIN_LEAD       = 'Connectez-vous avec votre compte Spi.';
export const LOGIN_EMAIL      = 'Email';
export const LOGIN_EMAIL_HINT = 'prenom.nom@spi.be';
export const LOGIN_PASSWORD   = 'Mot de passe';
export const LOGIN_SUBMIT     = 'Se connecter';
export const LOGIN_SUBMITTING = 'Connexion…';
export const LOGIN_NO_ACCOUNT = 'Pas de compte ? Adressez-vous à un administrateur de l\'application.';

export const SCAN_TAG_UNKNOWN          = 'Ce tag est inconnu.';
export const SCAN_IDENTITY_MISMATCH    = 'Ce numéro de mobile est déjà utilisé par une autre personne.';
export const SCAN_FAILED               = 'Scan impossible. Réessayez.';
export const CALIBRATION_TOO_IMPRECISE = 'Précision GPS insuffisante. Rapprochez-vous d\'une fenêtre ou sortez, puis réessayez.';
export const CALIBRATION_FAILED        = 'Calibration impossible.';
export const POSITION_UNAVAILABLE      = 'Position indisponible. Autorisez la géolocalisation puis rechargez.';
export const SCAN_BUSINESS          = 'Société :';
export const BUSINESS_CHANGED       = 'J\'ai changé de société';
export const CHOOSE_BUSINESS        = 'Choisissez votre société';
export const CONFIRM_BUSINESS       = 'Confirmer ma société';
export const SAVING                 = 'Enregistrement…';
export const BUSINESS_CHANGE_FAILED = 'Changement de société impossible.';
// ---------- erreurs precises renvoyees par le back ----------
export const BCE_ALREADY_USED          = 'Ce numéro BCE est déjà utilisé par une autre société.';
export const PROJECT_CODE_ALREADY_USED = 'Ce code projet est déjà utilisé par un autre bâtiment.';
export const ADDRESS_ALREADY_USED      = 'Cette adresse est déjà utilisée par un autre bâtiment.';
export const WING_NAME_ALREADY_USED    = 'Ce bâtiment a déjà une aile portant ce nom.';
export const WING_ALREADY_TAGGED       = 'Cette aile a déjà un tag.';
export const MOBILE_ALREADY_USED       = 'Ce numéro de mobile est déjà utilisé par un autre technicien.';
export const EMAIL_ALREADY_USED        = 'Cette adresse email est déjà utilisée par un autre compte.';

export const BUILDING_NOT_EMPTY   = 'Suppression impossible : ce bâtiment contient encore des ailes.';
export const WING_NOT_EMPTY       = 'Suppression impossible : cette aile a encore un tag.';
export const TAG_NOT_EMPTY        = 'Suppression impossible : ce tag a des interventions enregistrées.';
export const BUSINESS_NOT_EMPTY   = 'Suppression impossible : cette société a encore des techniciens.';
export const TECHNICIAN_NOT_EMPTY = 'Suppression impossible : ce technicien a des interventions enregistrées.';

export const WRONG_CREDENTIALS = 'Email ou mot de passe incorrect.';
export const ACCOUNT_LOCKED    = 'Compte verrouillé. Adressez-vous à un administrateur.';
export const WRONG_PASSWORD    = 'Mot de passe actuel incorrect.';
export const SAME_PASSWORD     = 'Le nouveau mot de passe doit être différent de l\'ancien.';

// ---------- carte ----------
export const OVERVIEW              = 'Vue d\'ensemble';
export const MAP                   = 'Carte';
export const REFRESH               = 'Actualiser';
export const MAP_TECHNICIANS_HERE  = 'Techniciens sur place';
export const MAP_QUIET_SITE        = 'Site calme';
export const MAP_UNCALIBRATED      = 'En attente de calibration';
export const MAP_UNCALIBRATED_HINT = 'Ces tags n\'ont pas encore de position : elle sera enregistrée automatiquement au premier scan précis effectué sur place.';
export const LOCATION              = 'Emplacement';
export const STATE                 = 'État';
export const MAP_REFRESHED_AT      = 'Actualisé à %s — rafraîchissement automatique chaque minute';
export const MAP_LOAD_FAILED       = 'Impossible de charger la carte.';
export const MAP_TECHNICIANS_COUNT = '%s technicien(s) sur place';
export const MAP_NO_ONGOING        = 'Aucune intervention en cours';
// ---------- localisation ----------
export const LOCATION_VERIFIED           = 'Position vérifiée';
export const LOCATION_TOO_FAR            = 'Trop loin du tag';
export const LOCATION_NO_GPS             = 'Sans GPS';
export const LOCATION_IMPRECISE          = 'GPS imprécis';
export const LOCATION_TAG_NOT_CALIBRATED = 'Tag non calibré';
export const NO_LOCATION                 = 'Non renseigné';

// ---------- profil ----------
export const ACCOUNT                = 'Compte';
export const MY_PROFILE             = 'Mon profil';
export const CHANGE_PASSWORD        = 'Changer mon mot de passe';
export const PASSWORD_CHANGED       = 'Mot de passe modifié.';
export const ROLE_ADMIN             = 'Admin';
export const ROLE_EMPLOYEE          = 'Employé';
export const SECURITY               = 'Sécurité';
export const CURRENT_PASSWORD       = 'Mot de passe actuel';
export const NEW_PASSWORD           = 'Nouveau mot de passe (8 caractères minimum)';
export const CONFIRM_PASSWORD       = 'Confirmer le nouveau mot de passe';
export const SAVE                   = 'Enregistrer';
export const NEW_PASSWORD_TOO_SHORT = 'Le nouveau mot de passe doit faire au moins 8 caractères.';

// ---------- communs aux pages de gestion ----------
export const MANAGEMENT = 'Gestion';
export const EDIT       = 'Modifier';
export const LOADING    = 'Chargement…';
export const NAME       = 'Nom';

// ---------- societes ----------
export const BUSINESSES                   = 'Sociétés';
export const NEW_BUSINESS_BUTTON          = '+ Nouvelle société';
export const SEARCH_BUSINESS              = 'Rechercher une société…';
export const BUSINESS                     = 'Société';
export const BCE                          = 'N° BCE';
export const DELETE_BLOCKED_BUSINESS_HINT = 'Suppression impossible : cette société a des techniciens';
export const NO_BUSINESS                  = 'Aucune société.';
export const EDIT_BUSINESS                = 'Modifier la société';
export const NEW_BUSINESS                 = 'Nouvelle société';
export const BUSINESS_NAME_HINT           = 'Ex : Électricité Dupont SPRL';
export const BCE_LABEL                    = 'N° BCE (10 chiffres, commence par 0 ou 1)';
export const BCE_HINT                     = 'Ex : 0123456749';
export const CREATE_BUSINESS              = 'Créer la société';

// ---------- navigation ----------
export const DASHBOARD      = 'Tableau de bord';
export const INTERVENTIONS  = 'Interventions';
export const BUILDINGS      = 'Bâtiments';
export const WINGS          = 'Ailes';
export const TAGS           = 'Tags NFC';
export const TECHNICIANS    = 'Techniciens';
export const ADMINISTRATION = 'Administration';
export const USERS          = 'Utilisateurs';
export const LOGOUT         = 'Déconnexion';

// ---------- page de scan ----------
export const SCAN_READING            = 'Lecture du tag…';
export const CONNECTED_AS            = 'Connecté :';
export const CALIBRATION_TITLE       = 'Calibration du tag';
export const CALIBRATED_ON           = 'Position enregistrée le';
export const GPS_ACCURACY            = 'Précision GPS actuelle : %s m';
export const CALIBRATE_HERE          = 'Calibrer ce tag ici';
export const CALIBRATE_HINT          = 'Appuyez uniquement lorsque vous êtes devant le tag.';
export const FIRST_VISIT             = 'Premier passage';
export const FIRST_VISIT_LEAD        = 'Identifiez-vous une seule fois : vous serez reconnu automatiquement lors des prochains passages.';
export const FIRSTNAME               = 'Prénom';
export const MOBILE_HINT             = 'Mobile — 04xx xx xx xx';
export const RECORD_ARRIVAL          = 'Enregistrer mon arrivée';
export const ARRIVAL_RECORDED        = 'Arrivée enregistrée';
export const DEPARTURE_RECORDED      = 'Départ enregistré';
export const RESCAN_HINT             = 'Rescannez le tag en partant pour enregistrer votre départ.';
export const TOO_FAR_WARNING         = 'Vous êtes à %s m du tag — ce passage sera signalé.';
export const NO_GPS_HINT             = 'Position non relevée (localisation refusée ou indisponible).';
export const IMPRECISE_HINT          = 'Position trop imprécise pour être confirmée — fréquent en intérieur.';
export const TAG_NOT_CALIBRATED_HINT = 'Ce tag n\'a pas encore de position enregistrée.';

// ---------- ailes ----------
export const NEW_WING_BUTTON         = '+ Nouvelle aile';
export const SEARCH_WING             = 'Rechercher une aile…';
export const WING                    = 'Aile';
export const BUILDING                = 'Bâtiment';
export const TAG                     = 'Tag';
export const TAG_PLACED              = 'Tag posé';
export const NO_TAG                  = 'Sans tag';
export const DELETE_BLOCKED_WING_HINT = 'Suppression impossible : cette aile a un tag';
export const NO_WING                 = 'Aucune aile.';
export const EDIT_WING               = 'Modifier l\'aile';
export const NEW_WING                = 'Nouvelle aile';
export const SELECT_BUILDING         = 'Sélectionner un bâtiment';
export const INFORMATION             = 'Informations';
export const WING_NAME               = 'Nom de l\'aile';
export const WING_NAME_HINT          = 'Ex : Aile Nord, Hall N°1, Bloc A';
export const CREATE_WING             = 'Créer l\'aile';

// ---------- techniciens ----------
export const TECHNICIANS_HINT               = 'Les techniciens sont créés automatiquement lors de leur premier scan. Vous pouvez corriger ici leur identité ou leur société.';
export const SEARCH_TECHNICIAN              = 'Rechercher un technicien, un mobile, une société…';
export const TECHNICIAN                     = 'Technicien';
export const MOBILE                         = 'Mobile';
export const DELETE_BLOCKED_TECHNICIAN_HINT = 'Suppression impossible : ce technicien a des interventions';
export const NO_TECHNICIAN                  = 'Aucun technicien.';
export const EDIT_TECHNICIAN                = 'Modifier le technicien';
export const IDENTITY                       = 'Identité';
export const FIRSTNAME_HINT                 = 'Ex : Marc';
export const LASTNAME_HINT                  = 'Ex : Dupont';
export const MOBILE_EXAMPLE                 = 'Ex : 0475 12 34 56';
export const ATTACHMENT                     = 'Rattachement';
export const SELECT_BUSINESS                = 'Sélectionner une société';
export const MOBILE_IS_IDENTITY             = 'Le téléphone mobile identifie le technicien lors de ses scans : deux techniciens ne peuvent pas avoir le même.';

// ---------- tags ----------
export const NEW_TAG_BUTTON          = '+ Nouveau tag';
export const TAGS_HINT               = 'Un tag par aile. L\'URL générée est celle à encoder sur le tag physique. La position s\'enregistre en scannant le tag sur place avec un téléphone connecté à l\'application ; « Recalibrer » la vide si le tag est déplacé.';
export const SEARCH_TAG              = 'Rechercher par bâtiment ou aile…';
export const GPS_COORDINATES         = 'Coordonnées GPS';
export const TAG_URL                 = 'URL du tag';
export const QR                      = 'QR';
export const QR_ALT                  = 'QR code du tag';
export const ENLARGE_QR              = 'Agrandir le QR code';
export const COPIED                  = 'Copié';
export const COPY_URL                = 'Copier l\'URL';
export const RECALIBRATE_HINT        = 'Recalibrer (scannez le tag sur place avec un téléphone connecté)';
export const DELETE_BLOCKED_TAG_HINT = 'Suppression impossible : ce tag a des interventions';
export const NO_TAG_ROW              = 'Aucun tag.';
export const EDIT_TAG                = 'Modifier le tag';
export const NEW_TAG                 = 'Nouveau tag';
export const WING_LABEL              = 'Aile (une aile ne peut avoir qu\'un seul tag)';
export const SELECT_WING             = 'Sélectionner une aile';
export const TAG_POSITION_NOTE       = 'La position GPS n\'est pas à encoder ici : scannez le tag sur place avec un téléphone connecté à l\'application.';
export const CREATE_TAG              = 'Créer le tag';
export const QR_HINT                 = 'Scannez ce QR avec l\'appareil photo du téléphone : la page de scan s\'ouvre. Pour écrire un tag NFC : ouvrez la page, copiez l\'URL depuis la barre d\'adresse, puis collez-la dans NFC Tools (Écrire → URL).';

// ---------- interventions ----------
export const EXPORT                    = 'Exporter';
export const EXPORTING                 = 'Export…';
export const SEARCH_INTERVENTION       = 'Rechercher par technicien, société, bâtiment…';
export const ALL_YEARS                 = 'Toutes les années';
export const ALL                       = 'Tout';
export const ONGOING                   = 'En cours';
export const DONE_FILTER               = 'Terminées';
export const ESTIMATED_FILTER          = 'Estimées';
export const ANOMALIES                 = 'Anomalies';
export const BUILDING_WING             = 'Bâtiment / Aile';
export const ARRIVAL                   = 'Arrivée';
export const DEPARTURE                 = 'Départ';
export const DURATION                  = 'Durée';
export const ESTIMATED                 = 'Estimé';
export const DONE                      = 'Terminé';
export const REMOTE_DEPARTURE          = 'Départ à distance';
export const NO_INTERVENTION           = 'Aucune intervention ne correspond.';
export const PREVIOUS                  = '‹ Précédente';
export const NEXT                      = 'Suivante ›';
export const DISTANCE_LABEL            = 'à %s m';
export const DEPARTURE_DISTANCE_LABEL  = 'à %s m au départ';
export const PAGE_LABEL                = 'Page %s sur %s — %s intervention(s)';
export const INTERVENTIONS_COUNT       = '%s intervention(s)';

// ---------- batiments ----------
export const NEW_BUILDING_BUTTON          = '+ Nouveau bâtiment';
export const SEARCH_BUILDING              = 'Rechercher un bâtiment…';
export const PROJECT_CODE                 = 'Code projet';
export const ADDRESS                      = 'Adresse';
export const BOX_PREFIX                   = 'bte';
export const DELETE_BLOCKED_BUILDING_HINT = 'Suppression impossible : ce bâtiment contient des ailes';
export const NO_BUILDING                  = 'Aucun bâtiment.';
export const EDIT_BUILDING                = 'Modifier le bâtiment';
export const NEW_BUILDING                 = 'Nouveau bâtiment';
export const BUILDING_NAME_HINT           = 'Ex : Bâtiment Cathédrale';
export const PROJECT_CODE_LABEL           = 'Code projet (4 à 8 caractères)';
export const PROJECT_CODE_HINT            = 'Ex : PRJ-001';
export const STREET                       = 'Rue';
export const STREET_HINT                  = 'Ex : Rue de la Cathédrale';
export const NUMBER                       = 'Numéro';
export const NUMBER_HINT                  = 'Ex : 10';
export const BOX_OPTIONAL                 = 'Boîte (optionnel)';
export const BOX_HINT                     = 'Ex : A';
export const POSTAL_CODE                  = 'Code postal';
export const POSTAL_CODE_HINT             = 'Ex : 4000';
export const CITY                         = 'Ville';
export const CITY_HINT                    = 'Ex : Liège';
export const CREATE_BUILDING              = 'Créer le bâtiment';
export const TAGS_COLUMN                  = 'Tags';

// ---------- utilisateurs ----------
export const NEW_USER_BUTTON          = '+ Nouvel utilisateur';
export const SEARCH_USER              = 'Rechercher un utilisateur…';
export const EMAIL                    = 'Email';
export const ROLE                     = 'Rôle';
export const YOU                      = 'vous';
export const LOCKED                   = 'Verrouillé';
export const UNLOCK_ACCOUNT           = 'Déverrouiller le compte';
export const NO_USER                  = 'Aucun utilisateur.';
export const EDIT_USER                = 'Modifier l\'utilisateur';
export const NEW_USER                 = 'Nouvel utilisateur';
export const USER_FIRSTNAME_HINT      = 'Ex : Marie';
export const USER_LASTNAME_HINT       = 'Ex : Dubois';
export const EMAIL_LABEL              = 'Email (prenom.nom@spi.be)';
export const EMAIL_HINT               = 'Ex : marie.dubois@spi.be';
export const ACCESS                   = 'Accès';
export const ROLE_EMPLOYEE_OPTION     = 'Employé — consulte et gère les données';
export const ROLE_ADMIN_OPTION        = 'Admin — gère en plus les utilisateurs';
export const NEW_PASSWORD_OPTIONAL    = 'Nouveau mot de passe (vide = inchangé)';
export const INITIAL_PASSWORD         = 'Mot de passe initial';
export const PASSWORD_UNCHANGED_HINT  = 'Laisser vide pour ne pas changer';
export const PASSWORD_TO_SHARE        = 'Mot de passe à communiquer';
export const HIDE                     = 'Masquer';
export const SHOW                     = 'Afficher';
export const CONFIRM_THE_PASSWORD     = 'Confirmer le mot de passe';
export const RETYPE_PASSWORD          = 'Retapez le même mot de passe';
export const PASSWORD_MISMATCH        = 'Les mots de passe ne correspondent pas.';
export const CREATE_USER              = 'Créer l\'utilisateur';

// ---------- tableau de bord ----------
export const PASSAGES                  = 'Passages';
export const ALL_INTERVENTIONS         = 'toutes interventions';
export const TOTAL_TIME                = 'Temps total';
export const ALL_BUSINESSES            = 'toutes sociétés';
export const TECHNICIANS_ON_SITE       = 'techniciens sur site';
export const ESTIMATED_DEPARTURES      = 'Départs estimés';
export const EXIT_NOT_SCANNED          = 'sortie non scannée (+1h)';
export const RECENT_INTERVENTIONS      = 'Interventions récentes';
export const NO_INTERVENTION_YET       = 'Aucune intervention pour le moment.';
export const RELIABILITY_BY_TECHNICIAN = 'Fiabilité de localisation par technicien';
export const FILTER_BY_TECHNICIAN      = 'Filtrer par technicien';
export const SCANS                     = 'Scans';
export const LOCATED                   = 'Localisés';
export const RATE                      = 'Taux';
export const NO_DATA                   = 'Aucune donnée.';
export const RELIABILITY_BY_BUSINESS   = 'Fiabilité de localisation par société';
export const FILTER_BY_BUSINESS        = 'Filtrer par société…';
export const TECHNICIAN_COUNT          = 'Techniciens';
export const FAR_SCANS_ALERT           = '%s intervention(s) scannée(s) loin du tag';
export const DASHBOARD_LOAD_FAILED     = 'Impossible de charger le tableau de bord.';

/** Regroupement pour les gabarits : un template Angular ne peut pas importer, il ne voit que le composant. */
export const MSG = {
  CANCEL, CLOSE, PASSWORD_SHOW, PASSWORD_HIDE,
  LOGIN_TITLE, LOGIN_LEAD, LOGIN_EMAIL, LOGIN_EMAIL_HINT,
  LOGIN_PASSWORD, LOGIN_SUBMIT, LOGIN_SUBMITTING, LOGIN_NO_ACCOUNT,
  SCAN_BUSINESS, BUSINESS_CHANGED, CHOOSE_BUSINESS, CONFIRM_BUSINESS, SAVING,
  MAP, REFRESH, MAP_TECHNICIANS_HERE, MAP_QUIET_SITE,
  MAP_UNCALIBRATED, MAP_UNCALIBRATED_HINT, LOCATION, STATE,
  ACCOUNT, MY_PROFILE, CHANGE_PASSWORD, PASSWORD_CHANGED, ROLE_ADMIN, ROLE_EMPLOYEE,
  SECURITY, CURRENT_PASSWORD, NEW_PASSWORD, CONFIRM_PASSWORD, SAVE,
  MANAGEMENT, BUSINESSES, NEW_BUSINESS_BUTTON, SEARCH_BUSINESS,
  OVERVIEW, DASHBOARD, INTERVENTIONS, BUILDINGS, WINGS, TAGS, TECHNICIANS,
  ADMINISTRATION, USERS, LOGOUT,
  BUSINESS, BCE, EDIT, DELETE_BLOCKED_BUSINESS_HINT, NO_BUSINESS, LOADING,
  EDIT_BUSINESS, NEW_BUSINESS, NAME, BUSINESS_NAME_HINT, BCE_LABEL, BCE_HINT, CREATE_BUSINESS,
  BUTTON_DELETE,
  SCAN_READING, CONNECTED_AS, CALIBRATION_TITLE, CALIBRATED_ON, CALIBRATE_HERE, CALIBRATE_HINT,
  FIRST_VISIT, FIRST_VISIT_LEAD, FIRSTNAME, MOBILE_HINT, RECORD_ARRIVAL,
  ARRIVAL_RECORDED, DEPARTURE_RECORDED, RESCAN_HINT,
  NO_GPS_HINT, IMPRECISE_HINT, TAG_NOT_CALIBRATED_HINT,
  NEW_WING_BUTTON, SEARCH_WING, WING, BUILDING, TAG, TAG_PLACED, NO_TAG,
  DELETE_BLOCKED_WING_HINT, NO_WING, EDIT_WING, NEW_WING,
  SELECT_BUILDING, INFORMATION, WING_NAME, WING_NAME_HINT, CREATE_WING,
  TECHNICIANS_HINT, SEARCH_TECHNICIAN, TECHNICIAN, MOBILE,
  DELETE_BLOCKED_TECHNICIAN_HINT, NO_TECHNICIAN, EDIT_TECHNICIAN,
  IDENTITY, FIRSTNAME_HINT, LASTNAME_HINT, MOBILE_EXAMPLE,
  ATTACHMENT, SELECT_BUSINESS, MOBILE_IS_IDENTITY,
  NEW_TAG_BUTTON, TAGS_HINT, SEARCH_TAG, GPS_COORDINATES, TAG_URL, QR,
  QR_ALT, ENLARGE_QR, COPIED, COPY_URL, RECALIBRATE_HINT,
  DELETE_BLOCKED_TAG_HINT, NO_TAG_ROW, EDIT_TAG, NEW_TAG,
  WING_LABEL, SELECT_WING, TAG_POSITION_NOTE, CREATE_TAG, QR_HINT,
  EXPORT, EXPORTING, SEARCH_INTERVENTION, ALL_YEARS, ALL, ONGOING,
  DONE_FILTER, ESTIMATED_FILTER, ANOMALIES, BUILDING_WING, ARRIVAL, DEPARTURE,
  DURATION, ESTIMATED, DONE, REMOTE_DEPARTURE, NO_INTERVENTION, PREVIOUS, NEXT,
  NEW_BUILDING_BUTTON, SEARCH_BUILDING, PROJECT_CODE, ADDRESS, BOX_PREFIX,
  DELETE_BLOCKED_BUILDING_HINT, NO_BUILDING, EDIT_BUILDING, NEW_BUILDING,
  BUILDING_NAME_HINT, PROJECT_CODE_LABEL, PROJECT_CODE_HINT, STREET, STREET_HINT,
  NUMBER, NUMBER_HINT, BOX_OPTIONAL, BOX_HINT, POSTAL_CODE, POSTAL_CODE_HINT,
  CITY, CITY_HINT, CREATE_BUILDING, TAGS_COLUMN,
  NEW_USER_BUTTON, SEARCH_USER, EMAIL, ROLE, YOU, LOCKED, UNLOCK_ACCOUNT,
  NO_USER, EDIT_USER, NEW_USER, USER_FIRSTNAME_HINT, USER_LASTNAME_HINT,
  EMAIL_LABEL, EMAIL_HINT, ACCESS, ROLE_EMPLOYEE_OPTION, ROLE_ADMIN_OPTION,
  NEW_PASSWORD_OPTIONAL, INITIAL_PASSWORD, PASSWORD_UNCHANGED_HINT, PASSWORD_TO_SHARE,
  HIDE, SHOW, CONFIRM_THE_PASSWORD, RETYPE_PASSWORD, PASSWORD_MISMATCH, CREATE_USER,
  PASSAGES, ALL_INTERVENTIONS, TOTAL_TIME, ALL_BUSINESSES, TECHNICIANS_ON_SITE,
  ESTIMATED_DEPARTURES, EXIT_NOT_SCANNED, RECENT_INTERVENTIONS, NO_INTERVENTION_YET,
  RELIABILITY_BY_TECHNICIAN, FILTER_BY_TECHNICIAN, SCANS, LOCATED, RATE, NO_DATA,
  RELIABILITY_BY_BUSINESS, FILTER_BY_BUSINESS, TECHNICIAN_COUNT,
};
