package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/save")
    public ResponseEntity<TeacherDTO>saveTeacher(@RequestBody TeacherDTO teacher){
        TeacherDTO savedTeacher=teacherService.saveTeacher(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    @GetMapping("/getTeacher/{teacherId}")
    public ResponseEntity<TeacherDTO>getTeacherById(@PathVariable String teacherId){
        TeacherDTO teacher=teacherService.getTeacherById(teacherId);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/getAllTeachers")
    public ResponseEntity<List<TeacherDTO>>getAllTeachers(){
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
}
