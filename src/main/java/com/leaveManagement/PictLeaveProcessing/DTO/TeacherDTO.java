package com.leaveManagement.PictLeaveProcessing.DTO;

import com.leaveManagement.PictLeaveProcessing.Entity.TeacherLeave;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id;
    private String teacherId;
    private String name;
    private String post;
    private String subject;
    private String department;
    private TeacherLeave leave;
}
