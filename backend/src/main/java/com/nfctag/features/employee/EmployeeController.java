package com.nfctag.features.employee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll(){
        return this.employeeService.findAll().stream().map(employeeMapper::toDto).toList();
    }

    @GetMapping("/employees/{id}")
    public EmployeeDTO findById(@PathVariable UUID id){
        return this.employeeMapper.toDto(this.employeeService.findById(id));
    }

    @PostMapping("/employees")
    public EmployeeDTO create(@Valid @RequestBody EmployeeDTO dto){
        Employee e = this.employeeMapper.toEntity(dto);
        Employee saved = this.employeeService.create(e);
        return this.employeeMapper.toDto(saved);
    }

    @PutMapping("/employees/{id}")
    public EmployeeDTO update(@PathVariable UUID id, @Valid @RequestBody EmployeeDTO dto){
        Employee e = this.employeeMapper.toEntity(dto);
        Employee updated = this.employeeService.update(id, e);
        return this.employeeMapper.toDto(updated);
    }

    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable UUID id){
        this.employeeService.delete(id);
    }
}