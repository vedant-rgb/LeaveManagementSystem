package com.leaveManagement.PictLeaveProcessing.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherNameDTO {
    private String teacherRegistrationId;
    private String name;
    private String post;
    private String subject;
    private String department;
}
