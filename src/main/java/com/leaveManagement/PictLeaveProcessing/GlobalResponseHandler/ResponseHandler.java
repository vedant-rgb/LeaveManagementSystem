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
public class ResponseHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getContainingClass().isAnnotationPresent(RestController.class) &&
                returnType.getMethod().getReturnType().equals(
                        ResponseEntity.class)) {
            log.info("Inside 1");
            return new ApiResponse<>(body);
        }
        if(body instanceof ApiResponse<?>){
            log.info("Inside 2");
            return body;
        }
        log.info("Inside 3");
        return new ApiResponse<>(body);
    }
}