package com.nfctag.features.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeDTO toDto(Employee e){
        return new EmployeeDTO(
                e.getId(),
                e.getFirstname(),
                e.getLastname(),
                e.getEmail(),
                e.getRole()
        );
    }

    public Employee toEntity(EmployeeDTO dto){
        String passwordHash = null;
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            passwordHash = passwordEncoder.encode(dto.getPassword());
        }
        return new Employee(
                dto.getFirstname(),
                dto.getLastname(),
                dto.getEmail(),
                passwordHash,
                dto.getRole()
        );
    }
}