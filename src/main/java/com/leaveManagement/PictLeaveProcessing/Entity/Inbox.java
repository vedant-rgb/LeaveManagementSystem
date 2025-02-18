package com.leaveManagement.PictLeaveProcessing.Entity;

import com.leaveManagement.PictLeaveProcessing.Enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inbox")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inbox {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String senderTeacherRegistrationId;
    private String receiverTeacherRegistrationId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String message;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lecture_details_id", referencedColumnName = "id")
    private LectureDetails lectureDetails;


}
