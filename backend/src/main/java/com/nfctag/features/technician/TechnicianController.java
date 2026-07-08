package com.nfctag.features.technician;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TechnicianController {
    @Autowired
    private TechnicianMapper workerMapper;

    @Autowired
    private TechnicianService workerService;

    @GetMapping("/workers")
    public List<TechnicianDTO> findAll(@RequestParam(required = false)UUID id){
        List<Technician>workers;
        if(id!=null)
            workers = this.workerService.findByBusinessId(id);
        else
            workers = this.workerService.findAll();
        return workers.stream().map(workerMapper::toDto).toList();
    }

    @GetMapping("/workers/{id}")
    public TechnicianDTO findById(@PathVariable UUID id){
        Technician worker = this.workerService.findById(id);
        return this.workerMapper.toDto(worker);
    }

    @PostMapping("/workers")
    public TechnicianDTO create(@Valid @RequestBody TechnicianDTO dto){
        Technician w = this.workerMapper.toEntity(dto);
        Technician saved = this.workerService.create(w);
        return this.workerMapper.toDto(saved);
    }

    @PutMapping("/workers/{id}")
    public TechnicianDTO save(@PathVariable UUID id, @Valid @RequestBody TechnicianDTO dto){
        Technician w = this.workerMapper.toEntity(dto);
        Technician updated = this.workerService.update(id,w);
        return this.workerMapper.toDto(updated);
    }

    @DeleteMapping("/workers/{id}")
    public void delete(@PathVariable UUID id){
        this.workerService.delete(id);
    }
}
