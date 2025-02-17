package com.leaveManagement.PictLeaveProcessing.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AlternateArrangementDTO {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String division;
    private String subject;
    private Boolean accepted;
    private String originalTeacherId;
    private String substituteTeacherId;
}
