package com.nfctag.features.business;

import com.nfctag.features.technician.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    public List<Business> findAll(){
        return this.businessRepository.findAll();
    }

    public Business findById(UUID id){
        return this.businessRepository.findById(id).orElseThrow(()-> new BusinessNotFoundException("Business not found : "+ id));
    }

    public Business create(Business business){
        if(this.businessRepository.existsByBce(business.getBce())){
            throw new BusinessAlreadyExistsException("BCE already exists : "+ business.getBce());
        }
        return this.businessRepository.save(business);
    }

    public Business update(UUID id, Business business){
        Business existing = this.findById(id);
        if(this.businessRepository.existsByBceAndIdNot(business.getBce(),id)){
            throw new BusinessAlreadyExistsException("BCE already exists : "+ business.getBce());
        }
        existing.setName(business.getName());
        existing.setBce(business.getBce());
        return this.businessRepository.save(existing);
    }

    public void delete(UUID id){
        this.findById(id);
        long technicians = this.technicianRepository.countByBusinessId(id);
        if (technicians > 0) {
            throw new BusinessNotEmptyException(
                    "This company has " + technicians + " technician(s).");
        }
        this.businessRepository.deleteById(id);
    }

    public long count(){
        return this.businessRepository.count();
    }
}
