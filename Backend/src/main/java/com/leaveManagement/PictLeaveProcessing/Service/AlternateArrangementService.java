package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.LeaveApplicationMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.AlternateArrangementDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.AlternateArrangement;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Repository.AlternateArrangementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlternateArrangementService {

    private final AlternateArrangementRepository alternateArrangementRepository;

    @Secured({"ROLE_TEACHER"})
    public List<AlternateArrangementDTO> getAllottedArrangementForSubstituteTeacher(){
        User user = getCurrentuser();
        List<AlternateArrangement> arrangement = alternateArrangementRepository.findAlternateArrangementBySubstituteTeacherRegistrationId(user.getTeacherRegistrationId());
        return arrangement.stream()
                .map(LeaveApplicationMapper::toDTO)
                .toList();
    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
