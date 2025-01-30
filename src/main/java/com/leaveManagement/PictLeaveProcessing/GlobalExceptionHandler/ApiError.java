package com.leaveManagement.PictLeaveProcessing.GlobalExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError<T> {

    private String error;
    private List<String> subError;
    private HttpStatus statusCode;

}
