package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;


@Service
public class PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;

    @Value("${nfctag.timezone}")
    private String timezone;

    public List<Presence> findAll(){
        return this.presenceRepository.findAll();
    }

    /** Interventions en cours — celles sans scan de départ. */
    public List<Presence> findOngoing(){
        return this.presenceRepository.findByDepartedAtIsNull();
    }

    /** Recherche paginée et filtrée pour la page Interventions. */
    public Page<Presence> search(Integer year, String state, String query, int page, int size){
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.DESC, "arrivedAt")
        );

        Specification<Presence> spec = Specification
                .where(PresenceSpecifications.inYear(year, ZoneId.of(timezone)))
                .and(PresenceSpecifications.withState(state))
                .and(PresenceSpecifications.matching(query));

        return this.presenceRepository.findAll(spec, pageable);
    }

    public List<Presence> findByTechnicianId(UUID technicianId) {
        return this.presenceRepository.findByTechnicianId(technicianId);
    }

    public List<Presence> findByMobile(String mobile) {
        return this.presenceRepository.findByTechnicianMobile(mobile);
    }

    public List<Presence> findByBuildingId(UUID buildingId) {
        return this.presenceRepository.findByTagWingBuildingId(buildingId);
    }

    public List<Presence> findByTagId(UUID tagId) {
        return this.presenceRepository.findByTagId(tagId);
    }


    public void delete(UUID id){
        if (!this.presenceRepository.existsById(id)) {
            throw new PresenceNotFoundException("Presence not found : " + id);
        }
        this.presenceRepository.deleteById(id);
    }

    /** Compteurs des pastilles et années disponibles, pour l'année et la recherche en cours. */
    public SearchMetaDTO searchMeta(Integer year, String query){
        Specification<Presence> base = Specification
                .where(PresenceSpecifications.inYear(year, ZoneId.of(timezone)))
                .and(PresenceSpecifications.matching(query));

        return new SearchMetaDTO(
                this.presenceRepository.findDistinctYears(),
                this.presenceRepository.count(base),
                this.presenceRepository.count(base.and(PresenceSpecifications.withState("ongoing"))),
                this.presenceRepository.count(base.and(PresenceSpecifications.withState("done"))),
                this.presenceRepository.count(base.and(PresenceSpecifications.withState("estimated"))),
                this.presenceRepository.count(base.and(PresenceSpecifications.withState("suspect")))
        );
    }

    /** Toutes les lignes correspondant aux filtres, sans pagination — pour l'export. */
    public List<Presence> searchAll(Integer year, String state, String query){
        Specification<Presence> spec = Specification
                .where(PresenceSpecifications.inYear(year, ZoneId.of(timezone)))
                .and(PresenceSpecifications.withState(state))
                .and(PresenceSpecifications.matching(query));

        return this.presenceRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "arrivedAt"));
    }

}
