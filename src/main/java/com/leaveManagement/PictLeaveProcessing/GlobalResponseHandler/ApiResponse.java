package com.leaveManagement.PictLeaveProcessing.GlobalResponseHandler;

import com.leaveManagement.PictLeaveProcessing.GlobalExceptionHandler.ApiError;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private ApiError apiErrorResponse;

    public ApiResponse() {
        this.timeStamp=LocalDateTime.now();
    }

    public ApiResponse(ApiError apiErrorResponse) {
        this();
        this.apiErrorResponse = apiErrorResponse;
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiError getApiErrorResponse() {
        return apiErrorResponse;
    }

    public void setApiErrorResponse(ApiError apiErrorResponse) {
        this.apiErrorResponse = apiErrorResponse;
    }
}
