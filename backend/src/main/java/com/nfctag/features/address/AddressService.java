package com.nfctag.features.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address findById(UUID id){
        return this.addressRepository.findById(id).orElseThrow(()-> new AddressNotFoundException(id));
    }
    public Address update(UUID id, Address address){
        Address existing = this.findById(id);
        existing.setStreet(address.getStreet());
        existing.setNumber(address.getNumber());
        existing.setBox(address.getBox());
        existing.setPostalCode(address.getPostalCode());
        existing.setCity(address.getCity());
        return this.addressRepository.save(existing);
    }

}
