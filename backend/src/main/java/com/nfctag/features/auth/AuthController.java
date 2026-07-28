package com.nfctag.features.auth;

import com.nfctag.features.employee.EmployeeDTO;
import com.nfctag.features.employee.EmployeeMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping("/auth/login")
    public EmployeeDTO login(@Valid @RequestBody LoginDTO dto,
                             HttpServletRequest request, HttpServletResponse response){
        return this.employeeMapper.toDto(
                this.authService.login(this.authMapper.toCredentials(dto), request, response));
    }

    @PostMapping("/auth/logout")
    public void logout(HttpServletRequest request){
        this.authService.logout(request);
    }

    @GetMapping("/auth/me")
    public EmployeeDTO me(Authentication authentication){
        return this.employeeMapper.toDto(this.authService.me(authentication));
    }
}