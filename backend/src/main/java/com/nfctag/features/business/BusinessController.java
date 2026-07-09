package com.nfctag.features.business;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    @Autowired
    private BusinessMapper businessMapper;

    @GetMapping("/businesses")
    public List<BusinessDTO>findAll(){
        return this.businessService.findAll().stream().map(businessMapper::toDto).toList();
    }

    @GetMapping("/businesses/{id}")
    public BusinessDTO findById(@PathVariable UUID id){
        Business business= this.businessService.findById(id);
        return this.businessMapper.toDto(business);
    }

    @PostMapping("/businesses")
    public BusinessDTO create(@Valid @RequestBody BusinessDTO dto){
        Business b = this.businessMapper.toEntity(dto);
        Business saved = this.businessService.create(b);
        return this.businessMapper.toDto(saved);
    }

    @PutMapping("/businesses/{id}")
    public BusinessDTO save(@PathVariable UUID id, @Valid @RequestBody BusinessDTO dto){
        Business b = this.businessMapper.toEntity(dto);
        Business updated = this.businessService.update(id,b);
        return this.businessMapper.toDto(updated);
    }

    @DeleteMapping("/businesses/{id}")
    public void delete(@PathVariable UUID id){
        this.businessService.delete(id);
    }

}
