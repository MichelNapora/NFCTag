package com.nfctag.features.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.employee.validation.SpiEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class EmployeeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @SpiEmail
    private String email;

    @NotNull
    private Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public EmployeeDTO(UUID id, String firstname, String lastname, String email, Role role){
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.role=role;
    }

    public EmployeeDTO(){}

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

    public Role getRole(){
        return this.role;
    }

    public String getPassword(){
        return this.password;
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

    public void setRole(Role role){
        this.role=role;
    }

    public void setPassword(String password){
        this.password=password;
    }

}