package com.nfctag.features.presence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;

    public List<Presence> findAll(){
        return this.presenceRepository.findAll();
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

}
