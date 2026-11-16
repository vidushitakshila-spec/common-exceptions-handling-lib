package dev.vt.common.exception.handlers;


import dev.vt.common.exception.config.ErrorCode;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

    /*
DataIntegrityViolationException,
EmptyResultDataAccessException,
OptimisticLockingFailureException,
PessimisticLockingFailureException,
CannotCreateTransactionException,
 QueryTimeoutException
    * */

@RestControllerAdvice
@Order(4)
public class PersistenceExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        ErrorCode errorCode = determineErrorCode(ex); // Maps root cause to DUPLICATE_RECORD, DB_CONFLICT, etc.

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(errorCode.getDefaultStatus(), errorCode.getDefaultMessage());
        problem.setTitle("Database Constraint Violation");
        problem.setProperty("error_code", errorCode.getCode());
        problem.setProperty("traceId", MDC.get("traceId"));

        return ResponseEntity.status(errorCode.getDefaultStatus()).body(problem);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ProblemDetail handleEmptyResult(DataAccessException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "A database constraint violation occurred."
        );
        problem.setProperty("error_code", "ERR_DB_CONFLICT");
        return problem;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ProblemDetail handleOptimisticLockingFailure(OptimisticLockingFailureException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "A database constraint violation occurred."
        );
        problem.setProperty("error_code", "ERR_DB_CONFLICT");
        return problem;
    }

}
