package com.nfctag.features.employee;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String email) {
        super("Email already exists : " + email);
    }
}