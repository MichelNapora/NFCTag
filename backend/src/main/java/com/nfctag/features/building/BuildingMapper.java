package com.nfctag.features.building;

import com.nfctag.features.address.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildingMapper {
    @Autowired
    private AddressMapper addressMapper;

    public BuildingDTO toDto(Building b){
        return new BuildingDTO(
                b.getId(),
                b.getName(),
                b.getProjectCode(),
                addressMapper.toDto(b.getAddress())
        );
    }

    public Building toEntity(BuildingDTO dto) {
        Building b = new Building();
        b.setName(dto.getName());
        b.setProjectCode(dto.getProjectCode());
        b.setAddress(addressMapper.toEntity(dto.getAddress()));
        return b;
    }

}
