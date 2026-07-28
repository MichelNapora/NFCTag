package com.nfctag.features.auth;

import com.nfctag.features.employee.Employee;
import com.nfctag.features.employee.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final HttpSessionSecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public Employee login(Credentials credentials, HttpServletRequest request, HttpServletResponse response){
        Employee employee = this.employeeRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new InvalidCredentialsException("Email or password not correct"));

        if (!this.passwordEncoder.matches(credentials.password(), employee.getPasswordHash())) {
            throw new InvalidCredentialsException("Email or password not correct");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                employee.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name()))
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);

        return employee;
    }

    public void logout(HttpServletRequest request){
        SecurityContextHolder.clearContext();
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
    }

    public Employee me(Authentication authentication){
        return this.employeeRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new InvalidCredentialsException("Session invalid"));
    }
}