package com.nfctag.features.technician;

import com.nfctag.common.Messages;

public class TechnicianAlreadyExistsException extends RuntimeException {
    public TechnicianAlreadyExistsException(String mobile) {
        super(String.format(Messages.TECHNICIAN_EXISTS, mobile));
    }
}