package com.nfctag.common;

import com.nfctag.features.address.AddressNotFoundException;
import com.nfctag.features.auth.InvalidPasswordException;
import com.nfctag.features.building.BuildingAlreadyExistsException;
import com.nfctag.features.building.BuildingNotFoundException;
import com.nfctag.features.business.BusinessAlreadyExistsException;
import com.nfctag.features.business.BusinessNotFoundException;
import com.nfctag.features.employee.EmployeeAlreadyExistsException;
import com.nfctag.features.employee.EmployeeNotFoundException;
import com.nfctag.features.presence.PresenceNotFoundException;
import com.nfctag.features.scan.InvalidScanException;
import com.nfctag.features.location.InsufficientAccuracyException;
import com.nfctag.features.tag.TagAlreadyExistsException;
import com.nfctag.features.tag.TagNotFoundException;
import com.nfctag.features.technician.TechnicianAlreadyExistsException;
import com.nfctag.features.technician.TechnicianNotFoundException;
import com.nfctag.features.wing.WingAlreadyExistsException;
import com.nfctag.features.wing.WingNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nfctag.features.auth.InvalidCredentialsException;
import com.nfctag.features.employee.EmployeePasswordRequiredException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            AddressNotFoundException.class,
            BuildingNotFoundException.class,
            WingNotFoundException.class,
            BusinessNotFoundException.class,
            TechnicianNotFoundException.class,
            TagNotFoundException.class,
            EmployeeNotFoundException.class,
            PresenceNotFoundException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({
            BuildingAlreadyExistsException.class,
            WingAlreadyExistsException.class,
            BusinessAlreadyExistsException.class,
            TechnicianAlreadyExistsException.class,
            TagAlreadyExistsException.class,
            EmployeeAlreadyExistsException.class
    })
    public ResponseEntity<String> handleAlreadyExists(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Impossible to delete. Others datas are binded");
    }

    @ExceptionHandler({
            EmployeePasswordRequiredException.class,
            InvalidScanException.class,
            InsufficientAccuracyException.class,
            InvalidPasswordException.class
    })
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}