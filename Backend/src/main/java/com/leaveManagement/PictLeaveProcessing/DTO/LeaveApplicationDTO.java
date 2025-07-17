package com.leaveManagement.PictLeaveProcessing.DTO;

import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LeaveApplicationDTO {

    private Long id;
    private String teacherRegistrationId;
    private String applicantName;
    private String post;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType natureOfLeave;
    private String reason;
    private String leaveAddress;
    private String phoneNumber;
    private ApplicationStatus status;
    private List<AlternateArrangementDTO> alternateArrangements;
    private String imageURL;
}
