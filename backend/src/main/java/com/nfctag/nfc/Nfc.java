package com.nfctag.nfc;

import com.nfctag.wing.Wing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "nfc")
@Getter
@Setter
public class Nfc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nfc_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wing_id")
    private Wing wing;

    @Column(nullable = false)
    private String name;

    @Column(name = "scan_token", nullable = false, unique = true)
    private UUID scanToken;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    private OffsetDateTime updated;

    private OffsetDateTime archived;
}
