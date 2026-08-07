package com.nfctag.features.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8, message = "The new password must be at least 8 characters long")
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