package com.nfctag.features.wing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WingService {

    @Autowired
    private WingRepository wingRepository;

    public List<Wing> findAll(){
        return this.wingRepository.findAll();
    }

    public Wing findById(UUID id){
        return this.wingRepository.findById(id).orElseThrow(()-> new WingNotFoundException("Wing not found : "+ id));
    }

    public Wing create(Wing wing){
        if(this.wingRepository.existsByNameAndBuildingId(wing.getName(),wing.getBuilding().getId())){
            throw  new WingAlreadyExistsException("Wing "+ wing.getName()+ " already exists for this building");
        }
        return this.wingRepository.save(wing);
    }

    public Wing update(UUID id, Wing w){
        Wing existing = this.findById(id);
        if (this.wingRepository.existsByNameAndBuildingIdAndIdNot(w.getName(), w.getBuilding().getId(), id)) {
            throw new WingAlreadyExistsException( "Wing " + w.getName() + " already exists for this building");
        }
        existing.setName(w.getName());
        existing.setBuilding(w.getBuilding());
        return this.wingRepository.save(existing);
    }

    public void delete(UUID id){
        this.findById(id);
        this.wingRepository.deleteById(id);
    }

    public List<Wing>findByBuildingId(UUID buildingId){
        return this.wingRepository.findByBuildingId(buildingId);
    }

    public long count(){
        return this.wingRepository.count();
    }

}
