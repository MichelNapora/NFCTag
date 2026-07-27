package com.nfctag.features.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    @Value("${nfctag.max-accuracy-meters}")
    private double maxAccuracyMeters;

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
        if(this.tagRepository.existsByWingIdAndIdNot(tag.getWing().getId(), id)){
            throw new TagAlreadyExistsException("This wing already has a tag !");
        }
        existing.setLatitude(tag.getLatitude());
        existing.setLongitude(tag.getLongitude());
        existing.setWing(tag.getWing());
        return this.tagRepository.save(existing);
    }

    public Tag calibrate(UUID scanToken, TagPositionDTO position){
        Tag tag = this.tagRepository.findByScanToken(scanToken)
                .orElseThrow(() -> new TagNotFoundException("Tag not found : " + scanToken));

        if (position.getAccuracy() > maxAccuracyMeters) {
            throw new InsufficientAccuracyException(
                    "GPS accuracy is inaccurate (" + Math.round(position.getAccuracy())
                            + " m). Go outside and try again please");
        }

        tag.setLatitude(position.getLatitude());
        tag.setLongitude(position.getLongitude());
        tag.setCalibratedAt(OffsetDateTime.now());
        return this.tagRepository.save(tag);
    }

    public void delete(UUID id){
        this.findById(id);
        this.tagRepository.deleteById(id);
    }

}
