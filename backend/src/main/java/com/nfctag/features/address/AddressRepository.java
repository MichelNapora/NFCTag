package com.nfctag.features.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    boolean existsByStreetIgnoreCaseAndNumberAndBoxAndPostalCodeAndCityIgnoreCase(
            String street, int number, String box, int postalCode, String city);

    boolean existsByStreetIgnoreCaseAndNumberAndBoxAndPostalCodeAndCityIgnoreCaseAndIdNot(
            String street, int number, String box, int postalCode, String city, UUID id);
}