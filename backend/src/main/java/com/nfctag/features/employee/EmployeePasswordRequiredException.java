package com.nfctag.features.employee;

public class EmployeePasswordRequiredException extends RuntimeException {
    public EmployeePasswordRequiredException() {
        super("A password is required to create an employee");
    }
}