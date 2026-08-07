package com.nfctag.common;

public final class Messages {

    private Messages(){}

    // ---------- authentification ----------
    public static final String SESSION_INVALID     = "Session invalid";
    public static final String INVALID_CREDENTIALS = "Email or password not correct";
    public static final String ACCOUNT_LOCKED      = "Account locked";
    public static final String INVALID_PASSWORD    = "This password is invalid";
    public static final String SAME_PASSWORD       = "The new password must be different";
    public static final String PASSWORD_TOO_SHORT  = "The new password must be at least 8 characters long";
    public static final String PASSWORD_LENGTH     = "The password must be between 8 and 72 characters long";
    public static final String PASSWORD_REQUIRED   = "A password is required to create an employee";

    // ---------- formats de saisie ----------
    public static final String INVALID_NAME   = "Invalid name : letters, spaces, hyphens and apostrophes only";
    public static final String INVALID_MOBILE = "Invalid mobile number : 10 digits starting with 04, no spaces";
    public static final String INVALID_BCE    = "Invalid BCE number : 10 digits, starting with 0 or 1, wrong check digits";
    public static final String INVALID_EMAIL  = "The mail must be formatted as firstname.lastname@spi.be";

    // ---------- introuvable ----------
    public static final String BUILDING_NOT_FOUND   = "Building not found : %s";
    public static final String WING_NOT_FOUND       = "Wing not found : %s";
    public static final String TAG_NOT_FOUND        = "Tag not found : %s";
    public static final String BUSINESS_NOT_FOUND   = "Business not found : %s";
    public static final String TECHNICIAN_NOT_FOUND = "Technician not found : %s";
    public static final String EMPLOYEE_NOT_FOUND   = "Employee not found : %s";
    public static final String PRESENCE_NOT_FOUND   = "Presence not found : %s";

    // ---------- deja existant ----------
    public static final String BUILDING_EXISTS         = "Project code already exists : %s";
    public static final String BUILDING_ADDRESS_EXISTS = "A building already exists at this address : %s %d, %d %s";
    public static final String WING_EXISTS             = "Wing %s already exists for this building";
    public static final String TAG_EXISTS              = "This wing already has a tag !";
    public static final String BUSINESS_EXISTS         = "BCE already exists : %s";
    public static final String TECHNICIAN_EXISTS       = "Mobile already exists : %s";
    public static final String EMPLOYEE_EXISTS         = "Email already exists : %s";

    // ---------- suppression impossible ----------
    public static final String BUILDING_NOT_EMPTY   = "This building contains %d wing(s). Delete them first.";
    public static final String WING_NOT_EMPTY       = "This wing has a tag. Delete it first.";
    public static final String TAG_NOT_EMPTY        = "This tag has %d intervention(s) recorded.";
    public static final String BUSINESS_NOT_EMPTY   = "This company has %d technician(s).";
    public static final String TECHNICIAN_NOT_EMPTY = "This technician has %d intervention(s) recorded.";
    public static final String DATA_BOUND           = "Impossible to delete. Others datas are binded";

    // ---------- scan ----------
    public static final String SCAN_INCOMPLETE     = "The first scan needs firstname, lastname, mobile and business";
    public static final String SCAN_IDENTITY       = "Identity does not match. Check your firstname, lastname and business.";
    public static final String SCAN_TOO_MANY       = "Too many scans on this tag : %d per minute maximum. Wait a moment and try again.";
    public static final String LOCATION_INACCURATE = "GPS accuracy is too low (%d m). Go outside and try again.";
}