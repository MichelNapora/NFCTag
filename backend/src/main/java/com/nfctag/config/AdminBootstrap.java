package com.nfctag.config;

import com.nfctag.features.employee.Employee;
import com.nfctag.features.employee.EmployeeRepository;
import com.nfctag.features.employee.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${nfctag.admin-email}")
    private String adminEmail;

    @Value("${nfctag.admin-password}")
    private String adminPassword;

    public AdminBootstrap(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        if (this.employeeRepository.count() == 0) {
            this.employeeRepository.save(new Employee(
                    "Admin", "Spi", adminEmail,
                    passwordEncoder.encode(adminPassword), Role.ADMIN));
        }
    }
}