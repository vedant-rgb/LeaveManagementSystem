package com.leaveManagement.PictLeaveProcessing.Mapper;

import com.leaveManagement.PictLeaveProcessing.DTO.AlternateArrangementDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.AlternateArrangement;
import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LeaveApplicationMapper {

    private static TeacherRepository teacherRepository;

    @Autowired
    public LeaveApplicationMapper(TeacherRepository teacherRepository) {
        LeaveApplicationMapper.teacherRepository = teacherRepository;
    }

    // Convert LeaveApplication Entity to DTO
    public static LeaveApplicationDTO toDTO(LeaveApplication leaveApplication) {
        return LeaveApplicationDTO.builder()
                .id(leaveApplication.getId())
                .teacherRegistrationId(leaveApplication.getTeacherRegistrationId())
                .applicantName(leaveApplication.getApplicantName())
                .post(leaveApplication.getPost())
                .numberOfDays(leaveApplication.getNumberOfDays())
                .startDate(leaveApplication.getStartDate())
                .endDate(leaveApplication.getEndDate())
                .natureOfLeave(leaveApplication.getNatureOfLeave())
                .reason(leaveApplication.getReason())
                .leaveAddress(leaveApplication.getLeaveAddress())
                .phoneNumber(leaveApplication.getPhoneNumber())
                .status(leaveApplication.getStatus())
                .alternateArrangements(leaveApplication.getAlternateArrangements() != null ?
                        leaveApplication.getAlternateArrangements().stream()
                                .map(LeaveApplicationMapper::toDTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    // Convert LeaveApplication DTO to Entity
    public static LeaveApplication toEntity(LeaveApplicationDTO dto) {
        LeaveApplication leaveApplication = LeaveApplication.builder()
                .teacherRegistrationId(dto.getTeacherRegistrationId())
                .applicantName(dto.getApplicantName())
                .post(dto.getPost())
                .numberOfDays(dto.getNumberOfDays())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .natureOfLeave(dto.getNatureOfLeave())
                .reason(dto.getReason())
                .leaveAddress(dto.getLeaveAddress())
                .phoneNumber(dto.getPhoneNumber())
                .status(dto.getStatus())
                .build();

        // Map alternate arrangements if present
        if (dto.getAlternateArrangements() != null) {
            List<AlternateArrangement> alternateArrangements = dto.getAlternateArrangements().stream()
                    .map(arrangementDTO -> toEntity(arrangementDTO, leaveApplication))
                    .collect(Collectors.toList());
            leaveApplication.setAlternateArrangements(alternateArrangements);
        }

        return leaveApplication;
    }

    // Convert AlternateArrangement DTO to Entity
    public static AlternateArrangement toEntity(AlternateArrangementDTO dto, LeaveApplication leaveApplication) {
        Teacher originalTeacher = findTeacher(dto.getOriginalTeacherId());
        Teacher substituteTeacher = findTeacher(dto.getSubstituteTeacherId());

        return AlternateArrangement.builder()
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .division(dto.getDivision())
                .subject(dto.getSubject())
                .accepted(Optional.ofNullable(dto.getAccepted()).orElse(false)) // Default to false if null
                .originalTeacher(originalTeacher)
                .substituteTeacher(substituteTeacher)
                .leaveApplication(leaveApplication)
                .build();
    }

    // Convert AlternateArrangement Entity to DTO
    public static AlternateArrangementDTO toDTO(AlternateArrangement alternateArrangement) {
        return AlternateArrangementDTO.builder()
                .id(alternateArrangement.getId())
                .date(alternateArrangement.getDate())
                .startTime(alternateArrangement.getStartTime())
                .endTime(alternateArrangement.getEndTime())
                .division(alternateArrangement.getDivision())
                .subject(alternateArrangement.getSubject())
                .accepted(alternateArrangement.getAccepted())
                .originalTeacherId(alternateArrangement.getOriginalTeacher() != null ?
                        alternateArrangement.getOriginalTeacher().getTeacherRegistrationId() : null)
                .substituteTeacherId(alternateArrangement.getSubstituteTeacher() != null ?
                        alternateArrangement.getSubstituteTeacher().getTeacherRegistrationId() : null)
                .build();
    }

    // Utility method to find teacher by ID
    private static Teacher findTeacher(String teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));
    }
}
