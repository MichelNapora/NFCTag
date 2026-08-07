package com.nfctag.features.employee;

import com.nfctag.common.Messages;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(UUID id) {
        super(String.format(Messages.EMPLOYEE_NOT_FOUND, id));
    }
}