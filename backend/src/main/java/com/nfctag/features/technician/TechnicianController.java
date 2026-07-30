package com.nfctag.features.technician;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TechnicianController {
    @Autowired
    private TechnicianMapper technicianMapper;

    @Autowired
    private TechnicianService technicianService;

    @GetMapping("/technicians")
    public List<TechnicianDTO> findAll(@RequestParam(required = false)UUID id){
        List<Technician>workers;
        if(id!=null)
            workers = this.technicianService.findByBusinessId(id);
        else
            workers = this.technicianService.findAll();
        return workers.stream().map(technicianMapper::toDto).toList();
    }

    @GetMapping("/technicians/{id}")
    public TechnicianDTO findById(@PathVariable UUID id){
        Technician worker = this.technicianService.findById(id);
        return this.technicianMapper.toDto(worker);
    }

    @GetMapping("/technicians/count")
    public long count() {
        return this.technicianService.count();
    }

    @PostMapping("/technicians")
    public TechnicianDTO create(@Valid @RequestBody TechnicianDTO dto){
        Technician w = this.technicianMapper.toEntity(dto);
        Technician saved = this.technicianService.create(w);
        return this.technicianMapper.toDto(saved);
    }

    @PutMapping("/technicians/{id}")
    public TechnicianDTO save(@PathVariable UUID id, @Valid @RequestBody TechnicianDTO dto){
        Technician w = this.technicianMapper.toEntity(dto);
        Technician updated = this.technicianService.update(id,w);
        return this.technicianMapper.toDto(updated);
    }

    @DeleteMapping("/technicians/{id}")
    public void delete(@PathVariable UUID id){
        this.technicianService.delete(id);
    }
}
