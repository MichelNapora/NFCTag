package com.nfctag.features.scan;

import org.springframework.stereotype.Component;

@Component
public class ScanMapper {

    public ScanCommand toCommand(ScanRequestDTO dto){
        return new ScanCommand(
                dto.getDeviceToken(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getAccuracy(),
                dto.getFirstname(),
                dto.getLastname(),
                dto.getMobile(),
                dto.getBusinessId()
        );
    }

    public ScanResponseDTO toDto(ScanResult r){
        return new ScanResponseDTO(
                r.technician().getDeviceToken(),
                r.technician().getFirstname() + " " + r.technician().getLastname(),
                r.tag().getWing().getBuilding().getName(),
                r.tag().getWing().getName(),
                r.presence().getArrivedAt(),
                r.presence().getDepartedAt(),
                r.location().isVerified(),
                r.location().getStatus(),
                r.location().getDistanceMeters(),
                r.action()
        );
    }
}