package com.nfctag.building;

import com.nfctag.address.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "building")
@Getter
@Setter
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false)
    private String name;

    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "building_type")
    private String buildingType;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    private OffsetDateTime updated;

    private OffsetDateTime archived;
}
