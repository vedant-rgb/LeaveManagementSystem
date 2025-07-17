package com.leaveManagement.PictLeaveProcessing.Configs;

import com.leaveManagement.PictLeaveProcessing.Configs.LectureDetailsMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.InboxDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.LectureDetailsDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Inbox;
import com.leaveManagement.PictLeaveProcessing.Entity.LectureDetails;
import org.springframework.stereotype.Component;

@Component
public class InboxMapper {

    private final LectureDetailsMapper lectureDetailsMapper;

    public InboxMapper(LectureDetailsMapper lectureDetailsMapper) {
        this.lectureDetailsMapper = lectureDetailsMapper;
    }

    // Convert InboxDTO to Inbox Entity
    public Inbox toEntity(InboxDTO dto) {
        if (dto == null) {
            return null;
        }

        Inbox inbox = new Inbox();
        inbox.setSenderTeacherRegistrationId(dto.getSenderTeacherRegistrationId());
        inbox.setReceiverTeacherRegistrationId(dto.getReceiverTeacherRegistrationId());
        inbox.setStatus(dto.getStatus());
        inbox.setMessage(dto.getMessage());

        // Map LectureDetailsDTO to LectureDetails
        if (dto.getDetails() != null) {
            LectureDetails lectureDetails = lectureDetailsMapper.toEntity(dto.getDetails());
            inbox.setLectureDetails(lectureDetails);
        }

        return inbox;
    }

    // Convert Inbox Entity to InboxDTO
    public InboxDTO toDTO(Inbox inbox) {
        if (inbox == null) {
            return null;
        }

        InboxDTO dto = new InboxDTO();
        dto.setSenderTeacherRegistrationId(inbox.getSenderTeacherRegistrationId());
        dto.setReceiverTeacherRegistrationId(inbox.getReceiverTeacherRegistrationId());
        dto.setStatus(inbox.getStatus());
        dto.setMessage(inbox.getMessage());

        // Map LectureDetails to LectureDetailsDTO
        if (inbox.getLectureDetails() != null) {
            LectureDetailsDTO detailsDTO = lectureDetailsMapper.toDTO(inbox.getLectureDetails());
            dto.setDetails(detailsDTO);
        }

        return dto;
    }
}
