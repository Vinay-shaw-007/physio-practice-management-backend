package com.physiopro.cms_backend.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // If the endpoint returns a List (e.g. getAllPatients), wrap it.
        // If it's already an ApiResponse, leave it alone.
        if(body instanceof ApiResponse<?>) {
            return body;
        }
        // Allows Swagger/Spring Boot internal responses to pass through
        if (request.getURI().getPath().contains("/v3/api-docs")) {
            return body;
        }

        return new ApiResponse<>(body);
    }
}