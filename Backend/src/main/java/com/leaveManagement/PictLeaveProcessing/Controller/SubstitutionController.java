package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherNameDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherSubstitutionService;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/substitution")
public class SubstitutionController {

    private final TeacherSubstitutionService teacherSubstitutionService;

    public SubstitutionController(TeacherSubstitutionService teacherSubstitutionService) {
        this.teacherSubstitutionService = teacherSubstitutionService;
    }

    @GetMapping("/available-teachers")
    public List<TeacherNameDTO> getAvailableTeachers(
            @RequestParam("date") String dateStr,
            @RequestParam("startTime") String startTimeStr,
            @RequestParam("endTime") String endTimeStr) {

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        return teacherSubstitutionService.getAvailableTeachers(date,startTime,endTime);
    }

}
