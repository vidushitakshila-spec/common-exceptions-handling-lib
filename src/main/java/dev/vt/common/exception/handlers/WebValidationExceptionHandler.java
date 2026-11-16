package dev.vt.common.exception.handlers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(2)  // Evaluated first
public class WebValidationExceptionHandler {

    /*
  MethodArgumentNotValidException, ConstraintViolationException, BindException, MethodArgumentTypeMismatchException,
  Missing*Exception, HttpRequestMethodNotSupportedException, HttpMediaType*Exception, MaxUploadSizeExceededException, etc
    * */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleBodyValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request body.");
        // map field errors...
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleParamValidation(ConstraintViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid parameters.");
        // map parameter violations...
        return problem;
    }
}