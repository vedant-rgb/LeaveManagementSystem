package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherNameDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherSubstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/substitution")
public class TeacherSubstitutionController {

    @Autowired
    private TeacherSubstitutionService substitutionService;

    // Get available teachers for substitution
    @GetMapping("/available-teachers")
    public List<TeacherNameDTO> getAvailableTeachers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam String teacherId) {  // Exclude this teacher
        return substitutionService.getAvailableTeachers(date, startTime, endTime, teacherId);
    }


//    // Assign a substitute teacher
//    @PostMapping("/assign")
//    public String assignSubstituteTeacher(
//            @RequestParam String absentTeacherId,
//            @RequestParam String substituteTeacherId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
//        return substitutionService.assignSubstituteTeacher(absentTeacherId, substituteTeacherId, date, startTime, endTime);
//    }
}
