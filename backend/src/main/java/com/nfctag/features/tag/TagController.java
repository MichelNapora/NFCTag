package com.nfctag.features.tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TagController {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<TagDTO>findAll(@RequestParam(required = false)UUID buildingId){
        List<Tag>tags;
        if(buildingId !=null)
            tags= this.tagService.findByBuildingId(buildingId);
        else
            tags = this.tagService.findAll();
        return tags.stream().map(tagMapper::toDto).toList();
    }


    @GetMapping("/tags/{id}")
    public TagDTO findById(@PathVariable UUID id){
        Tag tag = this.tagService.findById(id);
        return this.tagMapper.toDto(tag);
    }

    @GetMapping("/tags/ids-in-use")
    public List<UUID> idsInUse(){
        return this.tagService.findIdsInUse();
    }

    @GetMapping("/tags/count")
    public long count() {
        return this.tagService.count();
    }

    @PostMapping("/tags")
    public TagDTO create(@Valid @RequestBody TagDTO dto){
        Tag tag = this.tagMapper.toEntity(dto);
        Tag saved = this.tagService.create(tag);
        return this.tagMapper.toDto(saved);
    }

    @PostMapping("/tags/calibrate/{scanToken}")
    public TagDTO calibrate(@PathVariable UUID scanToken, @Valid @RequestBody TagPositionDTO position){
        Tag calibrated = this.tagService.calibrate(scanToken,
                position.getLatitude(), position.getLongitude(), position.getAccuracy());
        return this.tagMapper.toDto(calibrated);
    }

    @PutMapping("/tags/{id}")
    public TagDTO update(@PathVariable UUID id,@Valid @RequestBody TagDTO dto){
        Tag tag = this.tagMapper.toEntity(dto);
        Tag updated = this.tagService.update(id,tag);
        return this.tagMapper.toDto(updated);
    }

    @DeleteMapping("/tags/{id}")
    public void delete(@PathVariable UUID id){
        this.tagService.delete(id);
    }
}
