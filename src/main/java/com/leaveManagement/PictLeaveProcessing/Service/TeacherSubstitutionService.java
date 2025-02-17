package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherNameDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherTimetable;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherTimetableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherSubstitutionService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherTimetableRepository timetableRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get free teachers for a given date and time slot
    public List<TeacherNameDTO> getAvailableTeachers(LocalDate date, LocalTime startTime, LocalTime endTime, String excludedTeacherId) {
        String day = getDayOfWeek(date);
        List<Teacher> teacherList = teacherRepository.findAvailableTeachersExcluding(day, startTime, endTime,excludedTeacherId);
        return teacherList.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherNameDTO.class))
                .toList();
    }




    // Utility method to get the day of the week from a LocalDate
    private String getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().name();
    }
}
