package com.nfctag.features.technician;

import com.nfctag.features.business.BusinessNotFoundException;
import com.nfctag.features.business.BusinessRepository;
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

    public List<Technician> findAll(){
        return this.technicianRepository.findAll();
    }

    public Technician findById(UUID id){
        return this.technicianRepository.findById(id).orElseThrow(()-> new TechnicianNotFoundException("Technician not found : "+ id));
    }

    public List<Technician>findByBusinessId(UUID id){
        if (!this.businessRepository.existsById(id)) {
            throw new BusinessNotFoundException("Business not found : "+ id);
        }
        return this.technicianRepository.findByBusinessId(id);
    }

    public Optional<Technician>findByMobile(String mobile){
        return this.technicianRepository.findByMobile(mobile);
    }

    public Technician create(Technician technician){
        if(this.technicianRepository.existsByMobile(technician.getMobile())){
            throw new TechnicianAlreadyExistsException("Mobile already exists : "+ technician.getMobile());
        }
        return this.technicianRepository.save(technician);
    }

    public Technician update(UUID id, Technician technician){
        Technician existing=this.findById(id);
        if(this.technicianRepository.existsByMobileAndIdNot(technician.getMobile(),id)){
            throw new TechnicianAlreadyExistsException("Mobile already exists : "+technician.getMobile());
        }
        existing.setFirstname(technician.getFirstname());
        existing.setLastname(technician.getLastname());
        existing.setMobile(technician.getMobile());
        existing.setBusiness(technician.getBusiness());
        return this.technicianRepository.save(existing);
    }

    public void delete (UUID id){
        this.findById(id);
        this.technicianRepository.deleteById(id);
    }
}
