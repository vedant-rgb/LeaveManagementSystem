package com.leaveManagement.PictLeaveProcessing.DTO;

import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlternateArrangementDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String division;
    private String subject;
    private Teacher substituteTeacher;
}