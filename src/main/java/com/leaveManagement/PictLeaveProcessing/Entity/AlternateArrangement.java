package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "AlternateArrangement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlternateArrangement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String division;
    private String subject;

    @ManyToOne
    @JoinColumn(name = "substitute_teacher_id", nullable = false)
    private Teacher substituteTeacher;

    @ManyToOne
    @JoinColumn(name = "leave_application_id", nullable = false)
    private LeaveApplication leaveApplication;
}
