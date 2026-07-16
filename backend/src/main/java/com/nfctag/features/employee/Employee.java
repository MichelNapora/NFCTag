package com.nfctag.features.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.employee.validation.SpiEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Employee {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @SpiEmail
    @Column(unique = true)
    private String email;

    @NotBlank
    private String passwordHash;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(String firstname, String lastname, String email, String passwordHash, Role role){
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.passwordHash=passwordHash;
        this.role=role;
    }

    public Employee(){}

    public UUID getId(){
        return this.id;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPasswordHash(){
        return this.passwordHash;
    }

    public Role getRole(){
        return this.role;
    }

    public void setFirstname(String firstname){
        this.firstname=firstname;
    }

    public void setLastname(String lastname){
        this.lastname=lastname;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash=passwordHash;
    }

    public void setRole(Role role){
        this.role=role;
    }

}