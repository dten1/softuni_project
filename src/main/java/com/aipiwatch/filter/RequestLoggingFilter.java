package com.aipiwatch.filter;

import com.aipiwatch.model.RequestLog;
import com.aipiwatch.service.RequestLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    private final RequestLogService requestLogService;

    public RequestLoggingFilter(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = System.currentTimeMillis() - start;
            RequestLog log = RequestLog.builder()
                    .method(request.getMethod())
                    .endpoint(request.getRequestURI())
                    .status(response.getStatus())
                    .durationMs(durationMs)
                    .timestamp(LocalDateTime.now())
                    .build();
            requestLogService.log(log);
        }
    }
}
