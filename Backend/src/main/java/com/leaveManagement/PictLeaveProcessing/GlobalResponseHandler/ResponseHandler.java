package com.leaveManagement.PictLeaveProcessing.GlobalResponseHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;  // Apply to all responses
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // ✅ If the response is a String, return it directly (Spring requires plain Strings for text responses)
        if (body instanceof String) {
            log.info("Inside String handling");
            return body;
        }

        // ✅ If the response is already wrapped in `ApiResponse`, return it as is.
        if (body instanceof ApiResponse<?>) {
            log.info("Inside ApiResponse handling");
            return body;
        }

        // ✅ Otherwise, wrap the response in `ApiResponse<T>`
        log.info("Inside default wrapping");
        return new ApiResponse<>(body);
    }
}
