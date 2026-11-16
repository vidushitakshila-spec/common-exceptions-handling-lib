package dev.vt.common.exception.config;

import dev.vt.common.exception.handlers.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        SecurityExceptionHandler.class,
        WebValidationExceptionHandler.class,
        PersistenceExceptionHandler.class,
        GlobalFallbackHandler.class
})
public class CommonExceptionAutoConfiguration {
    // Automatically registers all shared enterprise exception handlers when imported
}