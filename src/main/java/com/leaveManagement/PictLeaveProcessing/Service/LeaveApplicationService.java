package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.LeaveApplicationMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import com.leaveManagement.PictLeaveProcessing.Repository.LeaveApplicationRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveApplicationService {

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public String applyForLeave(LeaveApplicationDTO leaveApplicationDTO) {
        // Validate if teacher exists
        Teacher teacher = teacherRepository.findByTeacherRegistrationId(leaveApplicationDTO.getTeacherRegistrationId())
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with id : "+leaveApplicationDTO.getTeacherRegistrationId()));

        LeaveType natureOfLeave = leaveApplicationDTO.getNatureOfLeave();

        if(natureOfLeave==LeaveType.COMMON_LEAVE && teacher.getLeave().getCommonLeave()-leaveApplicationDTO.getNumberOfDays()<0){
            throw new IllegalStateException("Common Leaves are not sufficient");
        }
        else if(natureOfLeave==LeaveType.EARNED_LEAVE && teacher.getLeave().getEarnedLeaves()-leaveApplicationDTO.getNumberOfDays()<0){
            throw new IllegalStateException("Earned Leaves are not sufficient");
        }
        else if(natureOfLeave==LeaveType.MEDICAL_LEAVE && teacher.getLeave().getMedicalLeave()-leaveApplicationDTO.getNumberOfDays()<0){
            throw new IllegalStateException("Medical Leaves are not sufficient");
        }
        else if(natureOfLeave==LeaveType.C_OFF_LEAVE && teacher.getLeave().getC_off()-leaveApplicationDTO.getNumberOfDays()<0){
            throw new IllegalStateException("C_Off Leaves are not sufficient");
        }
        else if(natureOfLeave==LeaveType.LWP_LEAVE && teacher.getLeave().getLWP()-leaveApplicationDTO.getNumberOfDays()<0){
            throw new IllegalStateException("LWP Leaves are not sufficient");
        }

        // Convert DTO to entity
        LeaveApplication leaveApplication = LeaveApplicationMapper.toEntity(leaveApplicationDTO);
        leaveApplication.setStatus(ApplicationStatus.PENDING_TO_BE_SENT_TO_HOD); // Default status

        // Save leave application
        leaveApplicationRepository.save(leaveApplication);
        return "Leave application submitted successfully.";
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public LeaveApplicationDTO getLeavesOfTeacher(){
        User user = getCurrentuser();
        System.out.println("1");
        LeaveApplication leaveApplication = leaveApplicationRepository.getLeaveApplicationByTeacherRegistrationId(user.getTeacherRegistrationId());
        System.out.println("2");
        if(leaveApplication == null){
            throw new RuntimeException("No leave application found for Teacher ID: " + user.getTeacherRegistrationId());
        }
        System.out.println("3");

        return LeaveApplicationMapper.toDTO(leaveApplication);
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public LeaveApplicationDTO getLeaveById(Long leaveId) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave application not found."));
        return LeaveApplicationMapper.toDTO(leaveApplication);
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<LeaveApplicationDTO> getAllLeaves() {
        List<LeaveApplication> leaves = leaveApplicationRepository.findAll();
        return leaves.stream()
                .map(LeaveApplicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public String cancelLeave(Long leaveId) {
        if (!leaveApplicationRepository.existsById(leaveId)) {
            return "Error: Leave application not found.";
        }
        leaveApplicationRepository.deleteById(leaveId);
        return "Leave application canceled successfully.";
    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
