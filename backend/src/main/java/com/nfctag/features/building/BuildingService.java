package com.nfctag.features.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Building> findAll(){
        return this.buildingRepository.findAll();
    }

    public Building findById(UUID id){
        return this.buildingRepository.findById(id).orElseThrow(()-> new BuildingNotFoundException("Building not found : "+id));
    }

    public Building create(Building building){
        if(this.buildingRepository.existsByProjectCode(building.getProjectCode())){
            throw new BuildingAlreadyExistsException("Project code already exists : "+ building.getProjectCode());
        }
        return this.buildingRepository.save(building);
    }

    public void delete(UUID id){
        this.findById(id);
        this.buildingRepository.deleteById(id);
    }

    public Building update(UUID id, Building building){
        Building existing = this.findById(id);
        if(this.buildingRepository.existsByProjectCodeAndIdNot(building.getProjectCode(), id)){
            throw new BuildingAlreadyExistsException("Project code already exists : "+ building.getProjectCode());
        }
        existing.setName(building.getName());
        existing.setProjectCode(building.getProjectCode());
        existing.setAddress(building.getAddress());
        return this.buildingRepository.save(existing);
    }


}
