package com.nfctag.features.employee;

public class EmployeePasswordRequiredException extends RuntimeException {
    public EmployeePasswordRequiredException(String message) {
        super(message);
    }
}
