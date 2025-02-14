package com.leaveManagement.PictLeaveProcessing.DTO;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherTimetableDTO {
    private Long id;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String subject;
    private String classroom;
    private String division;
    private String teacherId;
}
