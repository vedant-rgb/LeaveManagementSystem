package com.leaveManagement.PictLeaveProcessing.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private String teacherRegistrationId;
    private String name;
    private String post;
    private String subject;
    private String department;
    private TeacherLeaveDTO leave;
}