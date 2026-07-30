package com.nfctag.features.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8, message = "Le nouveau mot de passe doit faire au moins 8 caractères")
    private String newPassword;

    public ChangePasswordDTO(){}

    public String getCurrentPassword(){
        return this.currentPassword;
    }

    public String getNewPassword(){
        return this.newPassword;
    }

    public void setCurrentPassword(String currentPassword){
        this.currentPassword=currentPassword;
    }

    public void setNewPassword(String newPassword){
        this.newPassword=newPassword;
    }
}