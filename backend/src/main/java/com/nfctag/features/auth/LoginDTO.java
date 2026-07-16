package com.nfctag.features.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public LoginDTO(){}

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }
}