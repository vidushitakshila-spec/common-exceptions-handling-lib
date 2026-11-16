package dev.vt.common.exception.config;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // --- 1. Security & Auth ---
    UNAUTHORIZED("ERR_UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "Authentication is required to access this resource."),
    ACCESS_DENIED("ERR_ACCESS_DENIED", HttpStatus.FORBIDDEN, "You do not have the required permissions or roles."),

    // --- 2. Web Validation & Binding ---
    VALIDATION_FAILED("ERR_VALIDATION_FAILED", HttpStatus.BAD_REQUEST, "One or more fields failed validation."),
    TYPE_MISMATCH("ERR_TYPE_MISMATCH", HttpStatus.BAD_REQUEST, "A request parameter or path variable has an invalid type."),
    MISSING_PARAMETER("ERR_MISSING_PARAMETER", HttpStatus.BAD_REQUEST, "A required request parameter or header is missing."),

    // --- 3. JSON Deserialization ---
    MALFORMED_JSON("ERR_MALFORMED_JSON", HttpStatus.BAD_REQUEST, "Malformed JSON syntax or unreadable payload structure."),
    INVALID_ENUM_VALUE("ERR_INVALID_ENUM_VALUE", HttpStatus.BAD_REQUEST, "Provided value does not match any allowed enum constants."),

    // --- 4. Database & Persistence ---
    DB_CONFLICT("ERR_DB_CONFLICT", HttpStatus.CONFLICT, "A database conflict occurred during the transaction."),
    DUPLICATE_RECORD("ERR_DUPLICATE_RECORD", HttpStatus.CONFLICT, "A record with this unique identifier already exists."),
    OPTIMISTIC_LOCK_FAILURE("ERR_OPTIMISTIC_LOCK_FAILURE", HttpStatus.CONFLICT, "The record was modified by another transaction. Please retry."),
    DB_TIMEOUT("ERR_DB_TIMEOUT", HttpStatus.GATEWAY_TIMEOUT, "Database execution timed out."),

    // --- 5. Routing & Infrastructure ---
    RESOURCE_NOT_FOUND("ERR_RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "The requested endpoint or resource could not be found."),
    METHOD_NOT_ALLOWED("ERR_METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not supported for this endpoint."),

    // --- 6. Custom Domain Business Rules ---
    BUSINESS_RULE_VIOLATION("ERR_BUSINESS_RULE_VIOLATION", HttpStatus.UNPROCESSABLE_ENTITY, "The business rule validation failed."),
    ACCOUNT_FROZEN("ERR_ACCOUNT_FROZEN", HttpStatus.FORBIDDEN, "The user account is currently frozen."),

    // --- 7. Global Fallback ---
    INTERNAL_SERVER_ERROR("ERR_INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please contact support.");

    private final String code;
    private final HttpStatus defaultStatus;
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus defaultStatus, String defaultMessage) {
        this.code = code;
        this.defaultStatus = defaultStatus;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getDefaultStatus() {
        return defaultStatus;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}