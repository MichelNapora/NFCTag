package com.nfctag.features.employee;

import com.nfctag.common.Messages;

public class EmployeePasswordRequiredException extends RuntimeException {
    public EmployeePasswordRequiredException() {
        super(Messages.PASSWORD_REQUIRED);
    }
}