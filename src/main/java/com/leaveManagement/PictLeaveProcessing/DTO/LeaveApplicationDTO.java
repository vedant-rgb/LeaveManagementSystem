package com.leaveManagement.PictLeaveProcessing.DTO;

import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApplicationDTO {
    private String applicantName;
    private String post;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType natureOfLeave;
    private String reason;
    private String leaveAddress;
    private String phoneNumber;
    List<AlternateArrangementDTO> alternateArrangement;
}
