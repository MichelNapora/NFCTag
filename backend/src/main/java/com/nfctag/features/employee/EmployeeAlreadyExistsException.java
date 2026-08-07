package com.nfctag.features.employee;

import com.nfctag.common.Messages;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String email) {
        super(String.format(Messages.EMPLOYEE_EXISTS, email));
    }
}