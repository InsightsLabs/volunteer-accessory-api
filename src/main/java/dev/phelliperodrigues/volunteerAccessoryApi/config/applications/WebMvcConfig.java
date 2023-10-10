package dev.phelliperodrigues.volunteerAccessoryApi.config.applications;

import dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.interceptors.TraceIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TraceIdInterceptor traceIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Adicione o interceptor à lista de interceptadores
        registry.addInterceptor(traceIdInterceptor);
    }
}