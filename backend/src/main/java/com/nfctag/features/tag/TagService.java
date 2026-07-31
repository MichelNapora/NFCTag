package com.nfctag.features.tag;

import com.nfctag.features.location.InsufficientAccuracyException;
import com.nfctag.features.presence.PresenceRepository;
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


    @Autowired
    private PresenceRepository presenceRepository;

    public List<Tag> findAll(){
        return this.tagRepository.findAll();
    }

    public List<Tag>findByBuildingId(UUID buildingId){
        return this.tagRepository.findByWingBuildingId(buildingId);
    }

    public Tag findById(UUID id){
        return this.tagRepository.findById(id).orElseThrow(()-> new TagNotFoundException(id));
    }

    public Tag create(Tag tag){
        if(this.tagRepository.existsByWingId(tag.getWing().getId())){
            throw new TagAlreadyExistsException();
        }
        return this.tagRepository.save(tag);
    }


    public Tag update(UUID id, Tag tag){
        Tag existing = this.findById(id);
        if(this.tagRepository.existsByWingIdAndIdNot(tag.getWing().getId(), id)){
            throw new TagAlreadyExistsException();
        }
        existing.setLatitude(tag.getLatitude());
        existing.setLongitude(tag.getLongitude());

        if (tag.getLatitude() == null) {
            existing.setCalibratedAt(null);
        }

        existing.setWing(tag.getWing());
        return this.tagRepository.save(existing);
    }

    public Tag calibrate(UUID scanToken, Double latitude, Double longitude, Double accuracy){
        Tag tag = this.tagRepository.findByScanToken(scanToken)
                .orElseThrow(() -> new TagNotFoundException(scanToken));

        if (accuracy > maxAccuracyMeters) {
            throw new InsufficientAccuracyException(
                    "GPS accuracy is too low (" + Math.round(accuracy)
                            + " m). Go outside and try again.");
        }

        tag.setLatitude(latitude);
        tag.setLongitude(longitude);
        tag.setCalibratedAt(OffsetDateTime.now());
        return this.tagRepository.save(tag);
    }


    public void delete(UUID id){
        this.findById(id);
        long presences = this.presenceRepository.countByTagId(id);
        if (presences > 0) {
            throw new TagNotEmptyException(presences);
        }
        this.tagRepository.deleteById(id);
    }

    public long count(){
        return this.tagRepository.count();
    }

}
