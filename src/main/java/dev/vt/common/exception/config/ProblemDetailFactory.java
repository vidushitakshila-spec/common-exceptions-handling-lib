package dev.vt.common.exception.config;


import org.slf4j.MDC;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

public class ProblemDetailFactory {

    private static final String TRACE_ID_KEY = "traceId";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String TIMESTAMP_KEY = "timestamp";

    /**
     * Creates a standardized ProblemDetail using the enum's default message.
     */
    public static ProblemDetail create(ErrorCode errorCode, WebRequest request) {
        return create(errorCode, errorCode.getDefaultMessage(), request, null);
    }

    /**
     * Creates a standardized ProblemDetail with a custom detail/message override.
     */
    public static ProblemDetail create(ErrorCode errorCode, String customDetail, WebRequest request) {
        return create(errorCode, customDetail, request, null);
    }

    /**
     * Creates a standardized ProblemDetail with custom detail and extra properties (e.g., field errors).
     */
    public static ProblemDetail create(ErrorCode errorCode, String customDetail, WebRequest request, Map<String, Object> additionalProperties) {
        String detailMessage = (customDetail != null && !customDetail.isBlank())
                ? customDetail
                : errorCode.getDefaultMessage();

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(errorCode.getDefaultStatus(), detailMessage);

        // Standard RFC 7807 fields
        problem.setTitle(errorCode.getDefaultStatus().getReasonPhrase());

        // Extract request URI path if available
        if (request instanceof ServletWebRequest servletWebRequest) {
            String uri = servletWebRequest.getRequest().getRequestURI();
            problem.setInstance(URI.create(uri));
        }

        // Custom Enterprise Payload Properties
        problem.setProperty(ERROR_CODE_KEY, errorCode.getCode());
        problem.setProperty(TIMESTAMP_KEY, Instant.now().toString());

        // Bind SLF4J MDC Trace ID for instant log correlation
        String traceId = MDC.get(TRACE_ID_KEY);
        if (traceId != null) {
            problem.setProperty(TRACE_ID_KEY, traceId);
        } else {
            problem.setProperty(TRACE_ID_KEY, "N/A");
        }

        // Inject any context-specific properties (e.g., validation field errors)
        if (additionalProperties != null && !additionalProperties.isEmpty()) {
            additionalProperties.forEach(problem::setProperty);
        }

        return problem;
    }
}