package com.nfctag.common;

import com.nfctag.features.auth.*;
import com.nfctag.features.building.BuildingAddressAlreadyExistsException;
import com.nfctag.features.building.BuildingAlreadyExistsException;
import com.nfctag.features.building.BuildingNotEmptyException;
import com.nfctag.features.building.BuildingNotFoundException;
import com.nfctag.features.business.BusinessAlreadyExistsException;
import com.nfctag.features.business.BusinessNotEmptyException;
import com.nfctag.features.business.BusinessNotFoundException;
import com.nfctag.features.employee.EmployeeAlreadyExistsException;
import com.nfctag.features.employee.EmployeeNotFoundException;
import com.nfctag.features.employee.EmployeePasswordRequiredException;
import com.nfctag.features.location.InsufficientAccuracyException;
import com.nfctag.features.presence.PresenceNotFoundException;
import com.nfctag.features.scan.InvalidScanException;
import com.nfctag.features.scan.ScanIdentityMismatchException;
import com.nfctag.features.scan.TooManyScansException;
import com.nfctag.features.tag.TagAlreadyExistsException;
import com.nfctag.features.tag.TagNotEmptyException;
import com.nfctag.features.tag.TagNotFoundException;
import com.nfctag.features.technician.TechnicianAlreadyExistsException;
import com.nfctag.features.technician.TechnicianNotEmptyException;
import com.nfctag.features.technician.TechnicianNotFoundException;
import com.nfctag.features.wing.WingAlreadyExistsException;
import com.nfctag.features.wing.WingNotEmptyException;
import com.nfctag.features.wing.WingNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ProblemDetail problem(HttpStatus status, RuntimeException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problem.setProperty("code", ex.getClass().getSimpleName());
        return problem;
    }

    @ExceptionHandler({
            BuildingNotFoundException.class,
            WingNotFoundException.class,
            BusinessNotFoundException.class,
            TechnicianNotFoundException.class,
            TagNotFoundException.class,
            EmployeeNotFoundException.class,
            PresenceNotFoundException.class
    })
    public ProblemDetail handleNotFound(RuntimeException ex) {
        return problem(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({
            BuildingAlreadyExistsException.class,
            WingAlreadyExistsException.class,
            BusinessAlreadyExistsException.class,
            TechnicianAlreadyExistsException.class,
            TagAlreadyExistsException.class,
            EmployeeAlreadyExistsException.class,
            BuildingAddressAlreadyExistsException.class
    })
    public ProblemDetail handleAlreadyExists(RuntimeException ex) {
        return problem(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({
            BuildingNotEmptyException.class,
            WingNotEmptyException.class,
            TagNotEmptyException.class,
            BusinessNotEmptyException.class,
            TechnicianNotEmptyException.class
    })
    public ProblemDetail handleNotEmpty(RuntimeException ex) {
        return problem(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, Messages.DATA_INTEGRITY);
        problem.setProperty("code", ex.getClass().getSimpleName());
        return problem;
    }

    @ExceptionHandler({
            EmployeePasswordRequiredException.class,
            InvalidScanException.class,
            InsufficientAccuracyException.class,
            InvalidPasswordException.class,
            SamePasswordException.class
    })
    public ProblemDetail handleBadRequest(RuntimeException ex) {
        return problem(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({
            InvalidCredentialsException.class,
            SessionInvalidException.class,
            AccountLockedException.class,
            ScanIdentityMismatchException.class
    })
    public ProblemDetail handleInvalidCredentials(RuntimeException ex) {
        return problem(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(TooManyScansException.class)
    public ProblemDetail handleTooManyScans(RuntimeException ex) {
        return problem(HttpStatus.TOO_MANY_REQUESTS, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fields.put(e.getField(), e.getDefaultMessage()));

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setProperty("code", "ValidationException");
        problem.setProperty("fields", fields);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex) {
        LOGGER.error("Unexpected error", ex);
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setProperty("code", "UnexpectedException");
        return problem;
    }
}