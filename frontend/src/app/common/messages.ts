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

/** Regroupement pour les gabarits : un template Angular ne peut pas importer, il ne voit que le composant. */
export const MSG = {
  CANCEL, CLOSE, PASSWORD_SHOW, PASSWORD_HIDE,
  LOGIN_TITLE, LOGIN_LEAD, LOGIN_EMAIL, LOGIN_EMAIL_HINT,
  LOGIN_PASSWORD, LOGIN_SUBMIT, LOGIN_SUBMITTING, LOGIN_NO_ACCOUNT,
  SCAN_BUSINESS, BUSINESS_CHANGED, CHOOSE_BUSINESS, CONFIRM_BUSINESS, SAVING,

};
