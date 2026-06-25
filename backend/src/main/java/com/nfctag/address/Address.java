package com.nfctag.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String street;

    private String number;

    private String box;

    @Column(name = "postal_code")
    private String postalCode;

    private String city;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    private OffsetDateTime updated;
}
