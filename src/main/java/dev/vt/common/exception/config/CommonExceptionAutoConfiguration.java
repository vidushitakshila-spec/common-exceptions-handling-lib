package dev.vt.common.exception.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ValidationExceptionHandler.class,
        DatabaseExceptionHandler.class,
        GlobalFallbackHandler.class
})
public class CommonExceptionAutoConfiguration {
    // Automatically registers all shared enterprise exception handlers when imported
}