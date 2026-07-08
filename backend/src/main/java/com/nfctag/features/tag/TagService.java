package com.nfctag.features.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll(){
        return this.tagRepository.findAll();
    }

    public List<Tag>findByBuildingId(UUID buildingId){
        return this.tagRepository.findByWingBuildingId(buildingId);
    }

    public Tag findById(UUID id){
        return this.tagRepository.findById(id).orElseThrow(()-> new TagNotFoundException("Tag not found : "+id));
    }

    public Tag create(Tag tag){
        if(this.tagRepository.existsByWingId(tag.getWing().getId())){
            throw new TagAlreadyExistsException("This wing already has a tag !");
        }
        return this.tagRepository.save(tag);
    }


    public Tag update(UUID id, Tag tag){
        Tag existing = this.findById(id);
        existing.setLatitude(tag.getLatitude());
        existing.setLongitude(tag.getLongitude());
        existing.setWing(tag.getWing());
        return this.tagRepository.save(existing);
    }

    public void delete(UUID id){
        this.findById(id);
        this.tagRepository.deleteById(id);
    }

}
