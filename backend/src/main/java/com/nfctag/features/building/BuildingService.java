package com.nfctag.features.building;

import com.nfctag.features.wing.WingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private WingRepository wingRepository;

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
        long wings = this.wingRepository.countByBuildingId(id);
        if (wings > 0) {
            throw new BuildingNotEmptyException(
                    "This building contains " + wings + " wing(s). Delete them first.");
        }
        this.buildingRepository.deleteById(id);
    }

    public Building update(UUID id, Building building){
        Building existing = this.findById(id);
        if(this.buildingRepository.existsByProjectCodeAndIdNot(building.getProjectCode(), id)){
            throw new BuildingAlreadyExistsException("Project code already exists : "+ building.getProjectCode());
        }
        existing.setName(building.getName());
        existing.setProjectCode(building.getProjectCode());
        existing.getAddress().setStreet(building.getAddress().getStreet());
        existing.getAddress().setNumber(building.getAddress().getNumber());
        existing.getAddress().setBox(building.getAddress().getBox());
        existing.getAddress().setPostalCode(building.getAddress().getPostalCode());
        existing.getAddress().setCity(building.getAddress().getCity());
        return this.buildingRepository.save(existing);
    }

    public long count(){
        return this.buildingRepository.count();
    }


}
