package com.nfctag.features.address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDTO toDto(Address a){
        return new AddressDTO(
                a.getId(),
                a.getStreet(),
                a.getNumber(),
                a.getBox(),
                a.getPostalCode(),
                a.getCity()
        );
    }
    public Address toEntity(AddressDTO dto){
        return new Address(
                dto.getStreet(),
                dto.getNumber(),
                dto.getBox() == null ? "" : dto.getBox(),
                dto.getPostalCode(),
                dto.getCity()
        );
    }
}
