package com.nfctag.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "presence")
@Getter
@Setter
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presence_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nfc_id", nullable = false)
    private Nfc nfc;

    @Column(name = "arrived_at", nullable = false)
    private OffsetDateTime arrivedAt;

    @Column(name = "departed_at")
    private OffsetDateTime departedAt;

    /** Départ estimé (le technicien n'a pas scanné en partant). */
    @Column(nullable = false)
    private boolean estimated = false;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    private OffsetDateTime updated;
}
