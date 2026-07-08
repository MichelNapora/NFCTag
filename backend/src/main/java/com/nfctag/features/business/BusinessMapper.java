package com.nfctag.features.business;

import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {
    public BusinessDTO toDto(Business b){
        return new BusinessDTO(
                b.getId(),
                b.getName(),
                b.getBce()
        );
    }
    public Business toEntity(BusinessDTO dto){
        Business b = new Business();
        b.setName(dto.getName());
        b.setBce(dto.getBce());
        return b;
    }
}
