package com.leaveManagement.PictLeaveProcessing.Entity;

import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "leave_application")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacherRegistrationId;

    private String applicantName;

    private String post;

    private Integer numberOfDays;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveType natureOfLeave;

    @Column(length = 500)
    private String reason;

    @Column(length = 255)
    private String leaveAddress;

    @Column(length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @OneToMany(mappedBy = "leaveApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlternateArrangement> alternateArrangements;
}
