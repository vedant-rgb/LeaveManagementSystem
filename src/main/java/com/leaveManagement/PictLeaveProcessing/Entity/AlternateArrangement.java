package com.leaveManagement.PictLeaveProcessing.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "alternate_arrangement")
@Getter
@Setter
@Builder
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

    private Boolean accepted;

    @ManyToOne
    @JoinColumn(name = "original_teacher_id",referencedColumnName = "teacherRegistrationId")
    private Teacher originalTeacher;  // Teacher whose lecture is being taken

    @ManyToOne
    @JoinColumn(name = "substitute_teacher_id", referencedColumnName = "teacherRegistrationId")
    private Teacher substituteTeacher;  // Teacher who is taking the class

    @ManyToOne
    @JoinColumn(name = "leave_application_id")
    @JsonIgnore
    private LeaveApplication leaveApplication;  // Reference to the LeaveApplication
}
