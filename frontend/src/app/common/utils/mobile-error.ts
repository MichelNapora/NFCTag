import { INVALID_MOBILE } from '../messages';
import { mobileDigits } from './mobile-digits';

/** Vérifie le mobile, comme le back (@ValidMobile). Les espaces sont tolérés ici, ils seront retirés à l'envoi. */
export function mobileError(value: string): string | null {
  if (!value.trim()) { return null; }
  return /^04\d{8}$/.test(mobileDigits(value)) ? null : INVALID_MOBILE;
}
