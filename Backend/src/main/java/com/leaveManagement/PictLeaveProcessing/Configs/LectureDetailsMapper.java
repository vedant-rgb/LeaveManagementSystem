package com.leaveManagement.PictLeaveProcessing.Configs;

import com.leaveManagement.PictLeaveProcessing.DTO.LectureDetailsDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.LectureDetails;
import org.springframework.stereotype.Component;

@Component
public class LectureDetailsMapper {

    // Convert LectureDetailsDTO to LectureDetails Entity
    public LectureDetails toEntity(LectureDetailsDTO dto) {
        if (dto == null) {
            return null;
        }

        LectureDetails entity = new LectureDetails();
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setDivision(dto.getDivision());
        entity.setSubject(dto.getSubject());

        return entity;
    }

    // Convert LectureDetails Entity to LectureDetailsDTO
    public LectureDetailsDTO toDTO(LectureDetails entity) {
        if (entity == null) {
            return null;
        }

        return new LectureDetailsDTO(
                entity.getId(),
                entity.getDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getDivision(),
                entity.getSubject()
        );
    }
}
