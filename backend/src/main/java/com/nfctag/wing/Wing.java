package com.nfctag.wing;

import com.nfctag.building.Building;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "wing")
@Getter
@Setter
public class Wing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wing_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(nullable = false)
    private String name;

    private String street;

    private String number;

    private String box;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    private OffsetDateTime updated;

    private OffsetDateTime archived;
}
