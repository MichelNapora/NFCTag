package com.nfctag.features.auth;

import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public Credentials toCredentials(LoginDTO dto){
        return new Credentials(dto.getEmail(), dto.getPassword());
    }

    public PasswordChange toPasswordChange(ChangePasswordDTO dto){
        return new PasswordChange(dto.getCurrentPassword(), dto.getNewPassword());
    }
}