package com.nfctag.worker;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Jeton déposé dans le navigateur d'un technicien pour le reconnaître
 * automatiquement lors des passages suivants (même 12 mois plus tard).
 */
@Entity
@Table(name = "worker_device")
@Getter
@Setter
public class WorkerDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_device_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false, unique = true)
    private UUID token;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    @Column(name = "last_seen")
    private OffsetDateTime lastSeen;
}
