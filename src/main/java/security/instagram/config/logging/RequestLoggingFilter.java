package security.instagram.config.logging;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ket_ein17
 * @Date 10/28/2023
 */
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    /**
     * Writes a log message before the request is processed.
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.trace(message);
    }

    /**
     * Writes a log message after the request is processed.
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }
}
