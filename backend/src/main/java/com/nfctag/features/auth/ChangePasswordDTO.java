package com.nfctag.features.auth;

import com.nfctag.common.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8, message = Messages.PASSWORD_TOO_SHORT)
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