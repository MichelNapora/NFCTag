package com.nfctag.features.wing;

import com.nfctag.features.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WingService {

    @Autowired
    private WingRepository wingRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Wing> findAll(){
        return this.wingRepository.findAll();
    }

    public List<UUID> findIdsInUse(){
        return this.wingRepository.findAll().stream()
                .map(Wing::getId)
                .filter(this::isInUse)
                .toList();
    }

    private boolean isInUse(UUID id){
        return this.tagRepository.existsByWingId(id);
    }

    public Wing findById(UUID id){
        return this.wingRepository.findById(id).orElseThrow(()-> new WingNotFoundException(id));
    }

    public Wing create(Wing wing){
        if(this.wingRepository.existsByNameAndBuildingId(wing.getName(),wing.getBuilding().getId())){
            throw  new WingAlreadyExistsException(wing.getName());
        }
        return this.wingRepository.save(wing);
    }

    public Wing update(UUID id, Wing w){
        Wing existing = this.findById(id);
        if (this.wingRepository.existsByNameAndBuildingIdAndIdNot(w.getName(), w.getBuilding().getId(), id)) {
            throw new WingAlreadyExistsException(w.getName());
        }
        existing.setName(w.getName());
        existing.setBuilding(w.getBuilding());
        return this.wingRepository.save(existing);
    }

    public void delete(UUID id){
        this.findById(id);
        long tags = this.tagRepository.countByWingId(id);
        if (tags > 0) {
            throw new WingNotEmptyException();
        }
        this.wingRepository.deleteById(id);
    }

    public List<Wing>findByBuildingId(UUID buildingId){
        return this.wingRepository.findByBuildingId(buildingId);
    }

    public long count(){
        return this.wingRepository.count();
    }

}
