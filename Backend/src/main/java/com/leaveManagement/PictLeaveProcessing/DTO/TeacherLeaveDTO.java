package com.leaveManagement.PictLeaveProcessing.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherLeaveDTO {
    private Long commonLeave;
    private Long medicalLeave;
    private Long earnedLeaves;
    private Long C_off;
    private Long LWP;
}
