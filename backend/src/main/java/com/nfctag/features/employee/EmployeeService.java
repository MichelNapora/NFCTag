package com.nfctag.features.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll(){
        return this.employeeRepository.findAll();
    }

    public Employee findById(UUID id){
        return this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found : " + id));
    }

    public Employee create(Employee employee){
        if (this.employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmployeeAlreadyExistsException("Email already exists : " + employee.getEmail());
        }
        if (employee.getPasswordHash() == null) {
            throw new EmployeePasswordRequiredException("A password is required to create an employee");
        }
        return this.employeeRepository.save(employee);
    }

    public Employee update(UUID id, Employee employee){
        Employee existing = this.findById(id);
        if (this.employeeRepository.existsByEmailAndIdNot(employee.getEmail(), id)) {
            throw new EmployeeAlreadyExistsException("Email already exists : " + employee.getEmail());
        }
        existing.setFirstname(employee.getFirstname());
        existing.setLastname(employee.getLastname());
        existing.setEmail(employee.getEmail());
        existing.setRole(employee.getRole());
        if (employee.getPasswordHash() != null) {
            existing.setPasswordHash(employee.getPasswordHash());
        }
        return this.employeeRepository.save(existing);
    }

    public void delete(UUID id){
        this.findById(id);
        this.employeeRepository.deleteById(id);
    }
}