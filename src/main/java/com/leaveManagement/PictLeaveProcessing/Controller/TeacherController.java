package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/save")
    public ResponseEntity<Teacher>saveTeacher(Teacher teacher){
        log.info("Saving teacher ${}",teacher);
        Teacher savedTeacher=teacherService.saveTeacher(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    @GetMapping("/getTeacher/{teacherId}")
    public ResponseEntity<Teacher>getTeacherById(@PathVariable String teacherId){
        log.info("Inside getTeacherById for id: ${}",teacherId);
        Teacher teacher=teacherService.getTeacherById(teacherId);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/getAllTeachers")
    public ResponseEntity<List<Teacher>>getAllTeachers(){
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
}
