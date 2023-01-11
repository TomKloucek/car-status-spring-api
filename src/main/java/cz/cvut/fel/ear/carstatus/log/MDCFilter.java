package cz.cvut.fel.ear.carstatus.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class MDCFilter  extends OncePerRequestFilter {
    public static final String TRACE_ID = "trace-id";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
                String requestID = Optional.ofNullable(httpServletRequest.getHeader(TRACE_ID)).orElse(UUID.randomUUID().toString());
            MDC.put(TRACE_ID, requestID);
            httpServletResponse.setHeader(TRACE_ID, requestID);
            filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
