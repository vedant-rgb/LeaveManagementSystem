package com.leaveManagement.PictLeaveProcessing.DTO;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
