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
    private TechnicianRepository workerRepository;

    @Autowired
    private BusinessRepository businessRepository;

    public List<Technician> findAll(){
        return this.workerRepository.findAll();
    }

    public Technician findById(UUID id){
        return this.workerRepository.findById(id).orElseThrow(()-> new TechnicianNotFoundException("Worker not found : "+ id));
    }

    public List<Technician>findByBusinessId(UUID id){
        if (!this.businessRepository.existsById(id)) {
            throw new BusinessNotFoundException("Business not found : "+ id);
        }
        return this.workerRepository.findByBusinessId(id);
    }

    public Optional<Technician>findByMobile(String mobile){
        return this.workerRepository.findByMobile(mobile);
    }

    public Technician create(Technician worker){
        if(this.workerRepository.existsByMobile(worker.getMobile())){
            throw new TechnicianAlreadyExistsException("Mobile already exists : "+ worker.getMobile());
        }
        return this.workerRepository.save(worker);
    }

    public Technician update(UUID id, Technician worker){
        Technician existing=this.findById(id);
        if(this.workerRepository.existsByMobileAndIdNot(worker.getMobile(),id)){
            throw new TechnicianAlreadyExistsException("Mobile already exists : "+worker.getMobile());
        }
        existing.setFirstname(worker.getFirstname());
        existing.setLastname(worker.getLastname());
        existing.setMobile(worker.getMobile());
        existing.setBusiness(worker.getBusiness());
        return this.workerRepository.save(existing);
    }

    public void delete (UUID id){
        this.findById(id);
        this.workerRepository.deleteById(id);
    }
}
