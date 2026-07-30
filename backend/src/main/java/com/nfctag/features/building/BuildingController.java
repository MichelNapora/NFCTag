package com.nfctag.features.building;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BuildingMapper buildingMapper;

    @GetMapping("/buildings")
    public List<BuildingDTO> findAll(){
        return this.buildingService.findAll().stream().map(buildingMapper::toDto).toList();
    }

    @GetMapping("/buildings/{id}")
    public BuildingDTO findById(@PathVariable UUID id){
        Building building = this.buildingService.findById(id);
        return this.buildingMapper.toDto(building);
    }

    @GetMapping("/buildings/count")
    public long count() {
        return this.buildingService.count();
    }

    @PostMapping("/buildings")
    public BuildingDTO create(@Valid @RequestBody BuildingDTO dto){
        Building b = this.buildingMapper.toEntity(dto);
        Building saved = this.buildingService.create(b);
        return this.buildingMapper.toDto(saved);
    }

    @PutMapping("/buildings/{id}")
    public BuildingDTO update(@PathVariable UUID id, @Valid @RequestBody BuildingDTO dto){
        Building b = this.buildingMapper.toEntity(dto);
        Building updated = this.buildingService.update(id,b);
        return this.buildingMapper.toDto(updated);
    }

    @DeleteMapping("/buildings/{id}")
    public void delete(@PathVariable UUID id){
        this.buildingService.delete(id);
    }
}
