package security.instagram.config.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ket_ein17
 * @Date 10/28/2023
 */
@Repository
public class LogHttpTraceRepository extends InMemoryHttpTraceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogHttpTraceRepository.class);

    /**
     * call super.add(traceRequest) to store request in memory and log uri, method, response code of request
     *
     * @param traceRequest
     */
    public void add(HttpTrace traceRequest) {
        super.add(traceRequest);
        List<String> tracesHeaders = traceRequest.getRequest().getHeaders().get("x-amzn-trace-id");
        boolean hasTraceHeader = tracesHeaders != null && tracesHeaders.size() > 0;
        String tracedRequestId = hasTraceHeader ? tracesHeaders.get(0) : "undefined";
        LOGGER.trace("FINISH-REQUEST-[ID={}]: [uri: {}, method: {}, response: {} in {} in milliseconds ]", tracedRequestId, traceRequest.getRequest().getUri(), traceRequest.getRequest().getMethod(), traceRequest.getResponse().getStatus(), traceRequest.getTimeTaken());
    }
}