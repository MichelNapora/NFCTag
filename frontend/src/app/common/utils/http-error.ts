import { INVALID_DATA, NETWORK_ERROR, NOT_FOUND_ERROR, TOO_MANY_REQUESTS } from '../messages';
import { errorText } from './error-text';

export function errorMessage(e: any, fallback: string, conflict: string = fallback): string {
  const precis = errorText(e?.error?.code);
  if (precis) { return precis; }

  switch (e?.status) {
    case 0:   return NETWORK_ERROR;
    case 400: return INVALID_DATA;
    case 404: return NOT_FOUND_ERROR;
    case 409: return conflict;
    case 429: return TOO_MANY_REQUESTS;
    default:  return fallback;
  }
}
