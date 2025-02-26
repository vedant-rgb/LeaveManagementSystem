package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.AlternateArrangementDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherNameDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherTimetableRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class TeacherSubstitutionService {

    private final TeacherRepository teacherRepository;
    private final TeacherTimetableRepository timetableRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get free teachers for a given date and time slot
    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<TeacherNameDTO> getAvailableTeachers(LocalDate date, LocalTime startTime, LocalTime endTime) {
        String day = getDayOfWeek(date);
        User user = getCurrentuser();
        List<Teacher> teacherList = teacherRepository.findAvailableTeachersExcluding(day, startTime, endTime, user.getTeacherRegistrationId());
        return teacherList.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherNameDTO.class))
                .toList();
    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    // Utility method to get the day of the week from a LocalDate
    private String getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().name();
    }
}
