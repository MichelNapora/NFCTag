import {
  ACCOUNT_LOCKED, ADDRESS_ALREADY_USED, BCE_ALREADY_USED, BUILDING_NOT_EMPTY,
  BUSINESS_NOT_EMPTY, CALIBRATION_TOO_IMPRECISE, EMAIL_ALREADY_USED, MOBILE_ALREADY_USED,
  PROJECT_CODE_ALREADY_USED, SAME_PASSWORD, SCAN_IDENTITY_MISMATCH, TAG_NOT_EMPTY,
  TECHNICIAN_NOT_EMPTY, WING_ALREADY_TAGGED, WING_NAME_ALREADY_USED, WING_NOT_EMPTY,
  WRONG_CREDENTIALS, WRONG_PASSWORD
} from '../messages';

/**
 * Message français associé au code d'erreur du back.
 * null si le code est inconnu : l'appelant retombe alors sur le message générique du statut HTTP.
 */
export function errorText(code: string | undefined): string | null {
  switch (code) {
    case 'BusinessAlreadyExistsException':        return BCE_ALREADY_USED;
    case 'BuildingAlreadyExistsException':        return PROJECT_CODE_ALREADY_USED;
    case 'BuildingAddressAlreadyExistsException': return ADDRESS_ALREADY_USED;
    case 'WingAlreadyExistsException':            return WING_NAME_ALREADY_USED;
    case 'TagAlreadyExistsException':             return WING_ALREADY_TAGGED;
    case 'TechnicianAlreadyExistsException':      return MOBILE_ALREADY_USED;
    case 'EmployeeAlreadyExistsException':        return EMAIL_ALREADY_USED;

    case 'BuildingNotEmptyException':             return BUILDING_NOT_EMPTY;
    case 'WingNotEmptyException':                 return WING_NOT_EMPTY;
    case 'TagNotEmptyException':                  return TAG_NOT_EMPTY;
    case 'BusinessNotEmptyException':             return BUSINESS_NOT_EMPTY;
    case 'TechnicianNotEmptyException':           return TECHNICIAN_NOT_EMPTY;

    case 'InvalidCredentialsException':           return WRONG_CREDENTIALS;
    case 'AccountLockedException':                return ACCOUNT_LOCKED;
    case 'InvalidPasswordException':              return WRONG_PASSWORD;
    case 'SamePasswordException':                 return SAME_PASSWORD;

    case 'InsufficientAccuracyException':         return CALIBRATION_TOO_IMPRECISE;
    case 'ScanIdentityMismatchException':         return SCAN_IDENTITY_MISMATCH;

    default:                                      return null;
  }
}
