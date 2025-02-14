package com.leaveManagement.PictLeaveProcessing.Entity;

import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "leaveApplication")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;
    private String post;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType natureOfLeave;
    private String reason;
    private String leaveAddress;
    private String phoneNumber;
    @OneToMany(mappedBy = "leaveApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlternateArrangement> alternateArrangements;

}
