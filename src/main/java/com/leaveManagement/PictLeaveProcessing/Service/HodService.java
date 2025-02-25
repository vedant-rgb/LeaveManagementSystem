package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.LeaveApplicationMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Enums.LeaveType;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import com.leaveManagement.PictLeaveProcessing.Repository.LeaveApplicationRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HodService {

    private final LeaveApplicationRepository leaveApplicationRepository;
    private final TeacherRepository teacherRepository;

    public HodService(LeaveApplicationRepository leaveApplicationRepository,
                      TeacherRepository teacherRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public List<LeaveApplicationDTO> getLeaveApplicationsByStatus(ApplicationStatus status) {
        List<LeaveApplication> leaveApplications = leaveApplicationRepository.getLeaveApplicationByStatus(status);
        return leaveApplications.stream()
                .map(LeaveApplicationMapper::toDTO)
                .toList();
    }


    public String acceptLeaveApplication(Long applicationId) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Leave Application not found for application ID : " + applicationId));
        leaveApplication.setStatus(ApplicationStatus.APPROVED);

        String id = leaveApplication.getTeacherRegistrationId();
        Teacher teacher = teacherRepository.findByTeacherRegistrationId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found for id : " + id));
        LeaveType natureOfLeave = leaveApplication.getNatureOfLeave();
        Integer days = leaveApplication.getNumberOfDays();

        if(natureOfLeave==LeaveType.COMMON_LEAVE){
            Long commonLeave = teacher.getLeave().getCommonLeave();
            teacher.getLeave().setCommonLeave(commonLeave-days);
        }
        else if(natureOfLeave==LeaveType.EARNED_LEAVE){
            Long commonLeave = teacher.getLeave().getCommonLeave();
            teacher.getLeave().setCommonLeave(commonLeave-days);
        }
        else if(natureOfLeave==LeaveType.MEDICAL_LEAVE){
            Long medicalLeave = teacher.getLeave().getMedicalLeave();
            teacher.getLeave().setMedicalLeave(medicalLeave-days);
        }
        else if(natureOfLeave==LeaveType.C_OFF_LEAVE){
            Long C_off = teacher.getLeave().getC_off();
            teacher.getLeave().setC_off(C_off-days);
        }
        else if(natureOfLeave==LeaveType.LWP_LEAVE){
            Long LWP = teacher.getLeave().getLWP();
            teacher.getLeave().setCommonLeave(LWP-days);
        }

        leaveApplicationRepository.save(leaveApplication);
        teacherRepository.save(teacher);
        return "Leave Application accepted with application ID : "+applicationId;
    }

    public String rejectLeaveApplication(Long applicationId) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Leave Application not found for application ID : " + applicationId));
        leaveApplication.setStatus(ApplicationStatus.REJECTED);
        leaveApplicationRepository.save(leaveApplication);
        return "Leave Application rejected with application ID : "+applicationId;
    }
}
