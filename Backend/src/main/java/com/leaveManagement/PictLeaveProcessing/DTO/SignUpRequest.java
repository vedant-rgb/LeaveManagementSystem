package com.leaveManagement.PictLeaveProcessing.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignUpRequest {
    private String teacherRegistrationId;
    private String email;
    private String password;
    private String name;
    private String post;
    private String subject;
    private String department;
}
