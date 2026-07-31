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
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee create(Employee employee){
        if (this.employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmployeeAlreadyExistsException(employee.getEmail());
        }
        if (employee.getPasswordHash() == null) {
            throw new EmployeePasswordRequiredException();
        }
        return this.employeeRepository.save(employee);
    }

    public Employee update(UUID id, Employee employee){
        Employee existing = this.findById(id);
        if (this.employeeRepository.existsByEmailAndIdNot(employee.getEmail(), id)) {
            throw new EmployeeAlreadyExistsException(employee.getEmail());
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

    /** L'administrateur libère un compte verrouillé après trop d'échecs. */
    public Employee unlock(UUID id){
        Employee employee = this.findById(id);
        employee.setLocked(false);
        employee.setFailedAttempts(0);
        return this.employeeRepository.save(employee);
    }


    public void delete(UUID id){
        this.findById(id);
        this.employeeRepository.deleteById(id);
    }
}