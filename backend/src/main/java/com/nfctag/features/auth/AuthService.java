package com.nfctag.features.auth;

import com.nfctag.features.employee.Employee;
import com.nfctag.features.employee.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${nfctag.max-login-attempts}")
    private int maxLoginAttempts;

    private final HttpSessionSecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public Employee login(Credentials credentials, HttpServletRequest request, HttpServletResponse response){
        Employee employee = this.employeeRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (employee.isLocked()) {
            throw new AccountLockedException();
        }

        if (!this.passwordEncoder.matches(credentials.password(), employee.getPasswordHash())) {
            employee.setFailedAttempts(employee.getFailedAttempts() + 1);
            if (employee.getFailedAttempts() >= this.maxLoginAttempts) {
                employee.setLocked(true);
            }
            this.employeeRepository.save(employee);
            throw new InvalidCredentialsException();
        }

        employee.setFailedAttempts(0);
        this.employeeRepository.save(employee);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                employee.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name()))
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        if (request.getSession(false) != null) {
            request.changeSessionId();
        }
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
                .orElseThrow(() -> new SessionInvalidException());
    }

    /** L'employé connecté change son propre mot de passe. */
    public void changePassword(Authentication authentication, PasswordChange change){
        Employee employee = this.me(authentication);

        if (!this.passwordEncoder.matches(change.currentPassword(), employee.getPasswordHash())) {
            throw new InvalidPasswordException();
        }

        if (this.passwordEncoder.matches(change.newPassword(), employee.getPasswordHash())) {
            throw new SamePasswordException();
        }
        employee.setPasswordHash(this.passwordEncoder.encode(change.newPassword()));
        this.employeeRepository.save(employee);
    }
}