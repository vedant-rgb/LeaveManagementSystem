package com.leaveManagement.PictLeaveProcessing.GlobalExceptionHandler;

import com.leaveManagement.PictLeaveProcessing.Exceptions.InsufficientLeaveException;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError<?>> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiError response = ApiError.builder()
                .error(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND)
                .build();
        return buildErrorResponseEntity(response);
    }

    @ExceptionHandler(InsufficientLeaveException.class)
    public ResponseEntity<ApiError<?>> handleInsufficientLeaveException(InsufficientLeaveException ex){
        ApiError response = ApiError.builder()
                .error(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST)
                .build();
        return buildErrorResponseEntity(response);
    }

    private ResponseEntity buildErrorResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError,apiError.getStatusCode());
    }
}
