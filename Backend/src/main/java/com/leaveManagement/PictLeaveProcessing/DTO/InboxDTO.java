package com.leaveManagement.PictLeaveProcessing.DTO;

import com.leaveManagement.PictLeaveProcessing.Enums.RequestStatus;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InboxDTO {
    private Long id;
    private String senderTeacherRegistrationId;
    private String receiverTeacherRegistrationId;
    private RequestStatus status;
    private String message;
    private LectureDetailsDTO details;
}
