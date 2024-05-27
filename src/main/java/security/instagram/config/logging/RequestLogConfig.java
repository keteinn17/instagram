package security.instagram.config.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ket_ein17
 * @Date 10/28/2023
 */
@Configuration
public class RequestLogConfig {
    @Bean
    public RequestLoggingFilter logFilter() {
        RequestLoggingFilter filter = new RequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setBeforeMessagePrefix("REQUEST: [");
        return filter;
    }
}