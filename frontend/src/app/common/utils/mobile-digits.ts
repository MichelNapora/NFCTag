/** Ramène un mobile à sa seule écriture possible : « 0470 11 12 22 » et « +32470111222 » donnent « 0470111222 ». */
export function mobileDigits(value: string): string {
  const digits = value.replace(/[^\d]/g, '');
  return digits.startsWith('32') ? '0' + digits.substring(2) : digits;
}
