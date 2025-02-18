package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.LeaveApplicationMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Repository.LeaveApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HodService {

    private final LeaveApplicationRepository leaveApplicationRepository;

    public HodService(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    @Transactional
    public List<LeaveApplicationDTO> getLeaveApplicationsByStatus(ApplicationStatus status) {
        List<LeaveApplication> leaveApplications = leaveApplicationRepository.getLeaveApplicationByStatus(status);
        return leaveApplications.stream()
                .map(LeaveApplicationMapper::toDTO)
                .toList();
    }

    @Transactional
    public int updateStatusByTeacherRegistrationId(String teacherRegistrationId, ApplicationStatus status) {
        return leaveApplicationRepository.updateStatusByTeacherRegistrationId(teacherRegistrationId, status);
    }


}
