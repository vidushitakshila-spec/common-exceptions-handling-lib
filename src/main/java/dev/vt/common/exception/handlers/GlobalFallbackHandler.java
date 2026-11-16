package dev.vt.common.exception.handlers;

import dev.vt.common.exception.config.ErrorCode;
import dev.vt.common.exception.config.ProblemDetailFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE) // Evaluated last as a catch-all
public class GlobalFallbackHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        String traceId = MDC.get("traceId");

        // CRITICAL: Log the full stack trace internally with the traceId for operations teams
        log.error("Uncaught unexpected exception occurred [TraceId: {}]: {}", traceId, ex.getMessage(), ex);

        // Build sanitized public-facing problem detail (never expose raw exception class/message to client)
        ProblemDetail problem = ProblemDetailFactory.create(
                ErrorCode.INTERNAL_SERVER_ERROR,
                "An unexpected system error occurred. Please reference the traceId when contacting support.",
                request
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Content-Type-Options", "nosniff");

        return ResponseEntity
                .status(problem.getStatus())
                .headers(headers)
                .body(problem);
    }
}
