package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherLeaveDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherLeave;
import com.leaveManagement.PictLeaveProcessing.Exceptions.InsufficientLeaveException;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeaveService {

    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public LeaveService(TeacherService teacherService, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Get the remaining leave balance for a teacher.
     */
    public TeacherLeaveDTO findLeaveRemaining(String teacherId) {
        TeacherDTO teacherDTO = teacherService.getTeacherById(teacherId);
        return teacherDTO.getLeave();
    }

    public String takeLeave(String teacherId, String leaveType) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByTeacherId(teacherId);
        if(optionalTeacher.isPresent()){
            Teacher teacher = optionalTeacher.get();
            switch (leaveType) {
                case "commonLeave" -> {
                    Long days = teacher.getLeave().getCommonLeave();
                    if (days > 0) {
                        teacher.getLeave().setCommonLeave(days - 1);
                    } else {
                        throw new InsufficientLeaveException("Common leaves are Over..");
                    }
                }
                case "medicalLeave" -> {
                    Long days = teacher.getLeave().getMedicalLeave();
                    if (days > 0) {
                        teacher.getLeave().setMedicalLeave(days - 1);
                    } else {
                        throw new InsufficientLeaveException("Medical leaves are Over..");
                    }
                }
                case "earnedLeaves" -> {
                    Long days = teacher.getLeave().getEarnedLeaves();
                    if (days > 0) {
                        teacher.getLeave().setEarnedLeaves(days - 1);
                    } else {
                        throw new InsufficientLeaveException("Earned leaves are Over..");
                    }
                }
                case "C_off" -> {
                    Long days = teacher.getLeave().getC_off();
                    if (days > 0) {
                        teacher.getLeave().setC_off(days - 1);
                    } else {
                        throw new InsufficientLeaveException("C_Off leaves are Over..");
                    }
                }
                case "LWP" -> {
                    Long days = teacher.getLeave().getLWP();
                    if (days > 0) {
                        teacher.getLeave().setLWP(days - 1);
                    } else {
                        throw new InsufficientLeaveException("LWP leaves are Over..");
                    }
                }
            }
            teacherRepository.save(teacher);
        }
        return leaveType+" has Been Approved";
    }
}


//public String takeLeave(String teacherId, String leaveType) {
//    Optional<Teacher> optionalTeacher = teacherRepository.findByTeacherId(teacherId);
//
//    if (optionalTeacher.isPresent()) {
//        Teacher teacher = optionalTeacher.get();
//
//        TeacherLeave leave = teacher.getLeave();
//
//        if (leave.takeLeave(leaveType)) {
//            teacherRepository.save(teacher); // Save the updated leave count
//            return "Leave approved for " + leaveType;
//        } else {
//            return "Leave balance not available for " + leaveType;
//        }
//    } else {
//        return "Teacher not found";
//    }
//}