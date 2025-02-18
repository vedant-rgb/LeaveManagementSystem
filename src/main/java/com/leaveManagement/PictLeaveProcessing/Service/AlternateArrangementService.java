package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.LeaveApplicationMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.AlternateArrangementDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.AlternateArrangement;
import com.leaveManagement.PictLeaveProcessing.Repository.AlternateArrangementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlternateArrangementService {

    private final AlternateArrangementRepository alternateArrangementRepository;

    public List<AlternateArrangementDTO> getAllottedArrangementForSubstituteTeacher(String teacherRegistrationId){
        List<AlternateArrangement> arrangement = alternateArrangementRepository.findAlternateArrangementBySubstituteTeacherRegistrationId(teacherRegistrationId);
        return arrangement.stream()
                .map(LeaveApplicationMapper::toDTO)
                .toList();
    }

}
