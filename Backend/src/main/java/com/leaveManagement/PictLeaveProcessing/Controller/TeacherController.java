package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherTimetableDTO;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherService;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherTimetableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherTimetableService timetableService;

    public TeacherController(TeacherService teacherService, TeacherTimetableService timetableService) {
        this.teacherService = teacherService;
        this.timetableService = timetableService;
    }

    @GetMapping("/getTeacher")
    public ResponseEntity<TeacherDTO>getTeacherById(){
        TeacherDTO teacher=teacherService.getTeacherById();
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/getAllTeachers")
    public ResponseEntity<List<TeacherDTO>>getAllTeachers(){
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PostMapping("/add")
    public ResponseEntity<List<TeacherTimetableDTO>> addTimetable(
            @RequestBody List<TeacherTimetableDTO> timetableDTOs) {
        return ResponseEntity.ok(timetableService.addTimetables(timetableDTOs));
    }
}
