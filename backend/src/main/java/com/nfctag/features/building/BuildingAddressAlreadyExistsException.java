package com.nfctag.features.building;

public class BuildingAddressAlreadyExistsException extends RuntimeException {
    public BuildingAddressAlreadyExistsException(String street, int number, int postalCode, String city) {
        super("A building already exists at this address : "
                + street + " " + number + ", " + postalCode + " " + city);
    }
}