package com.nfctag.features.presence;

import org.springframework.stereotype.Component;

@Component
public class PresenceMapper {
  public PresenceDTO toDto(Presence presence,Long durationMinutes){

      return new PresenceDTO(
              presence.getId(),
              presence.getTechnician().getFirstname()+ " " +presence.getTechnician().getLastname(),
              presence.getTechnician().getMobile(),
              presence.getTechnician().getBusiness().getName(),
              presence.getTag().getWing().getBuilding().getName(),
              presence.getTag().getWing().getName(),
              presence.getArrivedAt(),
              presence.getDepartedAt(),
              durationCalculator.compute(presence),
              presence.isEstimated(),
              presence.isLocationVerified()
      );
  }

}
