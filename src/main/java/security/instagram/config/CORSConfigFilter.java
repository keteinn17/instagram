package security.instagram.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ket_ein17
 * @Date 5/28/2024
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSConfigFilter implements Filter{
    private Logger LOGGER = LoggerFactory.getLogger(CORSConfigFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String origin = request.getHeader("Origin");

        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, HEAD, OPTIONS, POST, PUT, REPORT, SEARCH, UPDATE, VERSION-CONTROL");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                "Key, Authorization, X-Forwarded-Email, " +
                "X-Forwarded-Original-UserId, X-Forwarded-UserId, X-Forwarded-UserName, X-Forwarded-Roles, " +
                "X-Forwarded-Services,  X-Forwarded-CompanyName");

        if (CorsUtils.isPreFlightRequest(request)) {
            LOGGER.info("Response preflight request {}", request.getServletPath());
            return;
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig arg0) {
    }
}
