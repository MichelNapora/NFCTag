package com.nfctag.features.wing;

import com.nfctag.features.building.Building;
import com.nfctag.features.building.BuildingNotFoundException;
import com.nfctag.features.building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WingMapper {
    @Autowired
    private BuildingRepository buildingRepository;

    public WingDTO toDto(Wing w){
        return new WingDTO(
                w.getId(),
                w.getName(),
                w.getBuilding().getId()
        );
    }

    public Wing toEntity(WingDTO dto){
        Building building= this.buildingRepository.findById(dto.getBuildingId()).orElseThrow(()-> new BuildingNotFoundException(("Building not found : "+ dto.getBuildingId())));
        return new Wing(dto.getName(),building);
    }
}
