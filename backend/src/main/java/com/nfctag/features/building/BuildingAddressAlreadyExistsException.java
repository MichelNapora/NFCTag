package com.nfctag.features.building;

import com.nfctag.common.Messages;

public class BuildingAddressAlreadyExistsException extends RuntimeException {
    public BuildingAddressAlreadyExistsException(String street, int number, int postalCode, String city) {
        super(String.format(Messages.BUILDING_ADDRESS_EXISTS, street, number, postalCode, city));
    }
}