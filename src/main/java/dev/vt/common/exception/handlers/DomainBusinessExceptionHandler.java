package dev.vt.common.exception.handlers;

import dev.vt.common.exception.config.ErrorCode;
import dev.vt.common.exception.config.ProblemDetailFactory;
import dev.vt.common.exception.exceptions.BusinessValidationException;
import dev.vt.common.exception.exceptions.ResourceNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(6)
public class DomainBusinessExceptionHandler {
    /*
    * BusinessValidationException, ResourceNotFoundException
    * */
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ProblemDetail> handleBusinessValidation(BusinessValidationException ex, WebRequest request) {
        // Automatically maps to ERR_BUSINESS_RULE_VIOLATION and 422 Unprocessable Entity
        ProblemDetail problem = ProblemDetailFactory.create(ErrorCode.BUSINESS_RULE_VIOLATION, ex.getMessage(), request);

        return ResponseEntity.status(problem.getStatus()).body(problem);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetailFactory.create(ErrorCode.RESOURCE_NOT_FOUND, ex.getMessage(), request);

        return ResponseEntity.status(problem.getStatus()).body(problem);
    }
}
