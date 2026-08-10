import { INVALID_DATA, NETWORK_ERROR, NOT_FOUND_ERROR, TOO_MANY_REQUESTS } from '../messages';

/**
 * Message français à afficher. Le back répond en anglais : son texte sert aux journaux, jamais à l'écran.
 * `conflict` dépend de l'action en cours : « existe déjà » à la création, « utilisé ailleurs » à la suppression.
 * Le reste (401, 403, 500…) retombe sur `fallback`, propre à l'écran appelant.
 */
export function errorMessage(e: any, fallback: string, conflict: string = fallback): string {
  switch (e?.status) {
    case 0:   return NETWORK_ERROR;
    case 400: return INVALID_DATA;
    case 404: return NOT_FOUND_ERROR;
    case 409: return conflict;
    case 429: return TOO_MANY_REQUESTS;
    default:  return fallback;
  }
}
