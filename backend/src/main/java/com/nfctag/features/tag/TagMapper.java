package com.nfctag.features.tag;

import com.nfctag.features.wing.Wing;
import com.nfctag.features.wing.WingNotFoundException;
import com.nfctag.features.wing.WingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    @Autowired
    private WingRepository wingRepository;

    public TagDTO toDto(Tag tag){
        return new TagDTO(
                tag.getId(),
                tag.getScanToken(),
                tag.getLatitude(),
                tag.getLongitude(),
                tag.getWing().getId()
        );
    }

    public Tag toEntity(TagDTO dto){
        Wing wing = this.wingRepository.findById(dto.getWingId()).orElseThrow(()->new WingNotFoundException("Wing not found : " + dto.getWingId()));
        return new Tag(wing,dto.getLatitude(),dto.getLongitude());
    }
}
