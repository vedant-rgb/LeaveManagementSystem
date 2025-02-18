package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.LeaveApplicationMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Repository.LeaveApplicationRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
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

    /**
     * Apply for leave (creates a new leave application)
     */
    @Transactional
    public String applyForLeave(LeaveApplicationDTO leaveApplicationDTO) {
        // Validate if teacher exists
        if (!teacherRepository.existsById(leaveApplicationDTO.getTeacherRegistrationId())) {
            return "Error: Teacher with ID " + leaveApplicationDTO.getTeacherRegistrationId() + " does not exist.";
        }

        // Convert DTO to entity
        LeaveApplication leaveApplication = LeaveApplicationMapper.toEntity(leaveApplicationDTO);
        leaveApplication.setStatus(ApplicationStatus.PENDING); // Default status

        // Save leave application
        leaveApplicationRepository.save(leaveApplication);
        return "Leave application submitted successfully.";
    }



    public LeaveApplicationDTO getLeaveByTeacherRegistrationId(String teacherRegistrationId){
        LeaveApplication leaveApplication = leaveApplicationRepository.getLeaveApplicationByTeacherRegistrationId(teacherRegistrationId);
        if(leaveApplication == null){
            throw new RuntimeException("No leave application found for Teacher ID: " + teacherRegistrationId);
        }
        return LeaveApplicationMapper.toDTO(leaveApplication);
    }

    /**
     * Get leave application by ID
     */
    @Transactional(readOnly = true)
    public LeaveApplicationDTO getLeaveById(Long leaveId) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave application not found."));
        return LeaveApplicationMapper.toDTO(leaveApplication);
    }

    /**
     * Get all leave applications
     */
    @Transactional(readOnly = true)
    public List<LeaveApplicationDTO> getAllLeaves() {
        List<LeaveApplication> leaves = leaveApplicationRepository.findAll();
        return leaves.stream()
                .map(LeaveApplicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cancel leave application (delete by ID)
     */
    @Transactional
    public String cancelLeave(Long leaveId) {
        if (!leaveApplicationRepository.existsById(leaveId)) {
            return "Error: Leave application not found.";
        }
        leaveApplicationRepository.deleteById(leaveId);
        return "Leave application canceled successfully.";
    }
}
