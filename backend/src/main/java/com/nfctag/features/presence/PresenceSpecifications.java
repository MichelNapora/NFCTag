package com.nfctag.features.presence;

import com.nfctag.features.location.LocationStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Filtres combinables de la page Interventions.
 * Chaque méthode renvoie null quand le filtre n'est pas demandé : Spring l'ignore alors.
 */
public final class PresenceSpecifications {

    private PresenceSpecifications(){}

    /** Interventions arrivées pendant l'année donnée. */
    public static Specification<Presence> inYear(Integer year, ZoneId zone){
        if (year == null) { return null; }

        OffsetDateTime start = ZonedDateTime.of(year, 1, 1, 0, 0, 0, 0, zone).toOffsetDateTime();
        OffsetDateTime end = start.plusYears(1);

        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get("arrivedAt"), start),
                cb.lessThan(root.get("arrivedAt"), end)
        );
    }

    /** État : ongoing, done, estimated, suspect. Tout le reste = pas de filtre. */
    public static Specification<Presence> withState(String state){
        if (state == null || state.isBlank() || state.equals("all")) { return null; }

        return switch (state) {
            case "ongoing"   -> (root, query, cb) -> cb.isNull(root.get("departedAt"));
            case "done"      -> (root, query, cb) -> cb.and(
                    cb.isNotNull(root.get("departedAt")),
                    cb.isFalse(root.get("estimated")));
            case "estimated" -> (root, query, cb) -> cb.isTrue(root.get("estimated"));
            case "suspect"   -> (root, query, cb) -> cb.equal(root.get("locationStatus"), LocationStatus.TOO_FAR);
            default          -> null;
        };
    }

    /** Recherche libre sur technicien, mobile, société, bâtiment et aile. */
    public static Specification<Presence> matching(String search){
        if (search == null || search.isBlank()) { return null; }

        String pattern = "%" + search.trim().toLowerCase() + "%";

        return (root, query, cb) -> {
            Join<Object, Object> technician = root.join("technician");
            Join<Object, Object> business = technician.join("business");
            Join<Object, Object> wing = root.join("tag").join("wing");
            Join<Object, Object> building = wing.join("building");

            return cb.or(
                    cb.like(cb.lower(technician.get("firstname")), pattern),
                    cb.like(cb.lower(technician.get("lastname")), pattern),
                    cb.like(cb.lower(technician.get("mobile")), pattern),
                    cb.like(cb.lower(business.get("name")), pattern),
                    cb.like(cb.lower(building.get("name")), pattern),
                    cb.like(cb.lower(wing.get("name")), pattern)
            );
        };
    }
}