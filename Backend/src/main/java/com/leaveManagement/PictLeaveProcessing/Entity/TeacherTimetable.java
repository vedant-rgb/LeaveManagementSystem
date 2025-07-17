package com.leaveManagement.PictLeaveProcessing.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "teacher_timetable")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private String day; // Example: "Monday", "Tuesday"

    @Column(name = "start_time")
    private LocalTime startTime; // Example: 10:00 AM

    @Column(name = "end_time")
    private LocalTime endTime; // Example: 11:00 AM

    @Column(name = "subject")
    private String subject;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "division")
    private String division; // Example: "A", "B", "C"

    @ManyToOne
    @JoinColumn(name = "teacher_registration_id") // Foreign Key to Teacher
    private Teacher teacher;

}
