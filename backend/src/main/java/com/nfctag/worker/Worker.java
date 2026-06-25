package com.nfctag.worker;

import com.nfctag.business.Business;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "worker")
@Getter
@Setter
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    private String lastname;

    private String firstname;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    private OffsetDateTime updated;

    private OffsetDateTime archived;

    /** Nom affichable : « Prénom Nom », ou le mobile à défaut. */
    @Transient
    public String displayName() {
        String full = ((firstname == null ? "" : firstname) + " "
                + (lastname == null ? "" : lastname)).trim();
        return full.isEmpty() ? mobile : full;
    }
}
