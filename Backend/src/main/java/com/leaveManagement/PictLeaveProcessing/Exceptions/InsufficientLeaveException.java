package com.leaveManagement.PictLeaveProcessing.Exceptions;

public class InsufficientLeaveException extends RuntimeException{
    public InsufficientLeaveException(String message) {
        super(message);
    }
}
