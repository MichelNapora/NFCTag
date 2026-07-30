package com.nfctag.features.wing;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class WingController {
    @Autowired
    private WingService wingService;

    @Autowired
    private WingMapper wingMapper;

    @GetMapping("/wings")
    public List<WingDTO>findAll(@RequestParam(required = false)UUID buildingId){
        List<Wing> wings;
        if(buildingId!=null){
            wings=this.wingService.findByBuildingId(buildingId);
        }else{
            wings=this.wingService.findAll();
        }
        return wings.stream().map(wingMapper::toDto).toList();
    }

    @GetMapping("/wings/{id}")
    public WingDTO findById(@PathVariable UUID id){
        Wing wing = this.wingService.findById(id);
        return this.wingMapper.toDto(wing);
    }

    @GetMapping("/wings/count")
    public long count() {
        return this.wingService.count();
    }

    @PostMapping("/wings")
    public WingDTO create(@Valid @RequestBody WingDTO dto){
        Wing w = this.wingMapper.toEntity(dto);
        Wing saved = this.wingService.create(w);
        return this.wingMapper.toDto(saved);
    }

    @PutMapping("/wings/{id}")
    public WingDTO update(@PathVariable UUID id, @Valid @RequestBody WingDTO dto){
        Wing w = this.wingMapper.toEntity(dto);
        Wing updated = this.wingService.update(id,w);
        return this.wingMapper.toDto(updated);
    }

    @DeleteMapping("/wings/{id}")
    public void delete(@PathVariable UUID id){
        this.wingService.delete(id);
    }
}
