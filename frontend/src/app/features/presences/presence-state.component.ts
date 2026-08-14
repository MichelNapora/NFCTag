import { Component, Input } from '@angular/core';
import { CommonModule, formatNumber } from '@angular/common';
import { PresenceView } from './presence.models';
import { locationLabelOf } from '../location/location.models';
import { DEPARTURE_DISTANCE_LABEL, DISTANCE_LABEL, MSG } from '../../common/messages';
import { format } from '../../common/utils/format';

/**
 * Colonne « état » d'une intervention : en cours / estimée / terminée,
 * puis le statut de localisation et la distance.
 * Partagée par le tableau de bord et la page des interventions, pour que
 * les deux écrans ne puissent pas afficher deux règles différentes.
 */
@Component({
  selector: 'app-presence-state',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './presence-state.component.html'
})
export class PresenceStateComponent {

  readonly msg = MSG;

  @Input({ required: true }) presence!: PresenceView;

  /** Affiche la distance du scan de départ. Seule la page des interventions l'utilise. */
  @Input() showDeparture = false;

  /** Une intervention est en cours tant que le départ n'a pas été scanné. */
  get isOngoing(): boolean {
    return this.presence.departedAt === null;
  }

  get locationLabel(): string {
    return locationLabelOf(this.presence.locationStatus);
  }

  /** « à 120 m », sous le statut de localisation. */
  get distanceLabel(): string {
    return this.presence.distanceMeters == null
      ? ''
      : format(DISTANCE_LABEL, formatNumber(this.presence.distanceMeters, 'fr', '1.0-0'));
  }

  /** « à 5 271 m au départ », quand le départ a été scanné trop loin. */
  get departureDistanceLabel(): string {
    return this.presence.departureDistanceMeters == null
      ? ''
      : format(DEPARTURE_DISTANCE_LABEL, formatNumber(this.presence.departureDistanceMeters, 'fr', '1.0-0'));
  }
}
