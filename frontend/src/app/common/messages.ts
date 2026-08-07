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
