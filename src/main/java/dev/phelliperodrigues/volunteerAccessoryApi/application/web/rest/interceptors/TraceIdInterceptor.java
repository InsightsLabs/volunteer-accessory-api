package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.interceptors;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TraceIdInterceptor implements HandlerInterceptor {
    private static final String TRACE_ID = "X-Trace-Id";
    private final Tracer tracer;

    public TraceIdInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (tracer == null || tracer.currentSpan() == null || tracer.currentSpan().context() == null) {
            return true;
        }
        response.addHeader(TRACE_ID, tracer.currentSpan().context().traceId());
        return true;
    }
}