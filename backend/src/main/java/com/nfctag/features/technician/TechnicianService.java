package com.nfctag.features.technician;

import com.nfctag.features.business.BusinessNotFoundException;
import com.nfctag.features.business.BusinessRepository;
import com.nfctag.features.presence.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    public List<Technician> findAll(){
        return this.technicianRepository.findAll();
    }

    public List<UUID> findIdsInUse(){
        return this.technicianRepository.findAll().stream()
                .map(Technician::getId)
                .filter(this::isInUse)
                .toList();
    }

    private boolean isInUse(UUID id){
        return this.presenceRepository.existsByTechnicianId(id);
    }

    public Technician findById(UUID id){
        return this.technicianRepository.findById(id).orElseThrow(()-> new TechnicianNotFoundException(id));
    }

    public List<Technician>findByBusinessId(UUID id){
        if (!this.businessRepository.existsById(id)) {
            throw new BusinessNotFoundException(id);
        }
        return this.technicianRepository.findByBusinessId(id);
    }

    public Optional<Technician>findByMobile(String mobile){
        return this.technicianRepository.findByMobile(mobile);
    }

    public Technician create(Technician technician){
        if(this.technicianRepository.existsByMobile(technician.getMobile())){
            throw new TechnicianAlreadyExistsException(technician.getMobile());
        }
        return this.technicianRepository.save(technician);
    }

    public Technician update(UUID id, Technician technician){
        Technician existing=this.findById(id);
        if(this.technicianRepository.existsByMobileAndIdNot(technician.getMobile(),id)){
            throw new TechnicianAlreadyExistsException(technician.getMobile());
        }
        existing.setFirstname(technician.getFirstname());
        existing.setLastname(technician.getLastname());
        existing.setMobile(technician.getMobile());
        existing.setBusiness(technician.getBusiness());
        return this.technicianRepository.save(existing);
    }

    public void delete (UUID id){
        this.findById(id);
        long presences = this.presenceRepository.countByTechnicianId(id);
        if (presences > 0) {
            throw new TechnicianNotEmptyException(presences);
        }
        this.technicianRepository.deleteById(id);
    }

    public long count(){
        return this.technicianRepository.count();
    }
}
