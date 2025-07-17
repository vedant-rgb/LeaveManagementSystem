package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherTimetableDTO;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherTimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
public class TeacherTimetableController {

    @Autowired
    private TeacherTimetableService timetableService;

    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherTimetableDTO>> getTimetableOfTeacher() {
        return ResponseEntity.ok(timetableService.getTimetableOfTeacher());
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<TeacherTimetableDTO>> getTimetableByDay(@PathVariable String day) {
        return ResponseEntity.ok(timetableService.getTimetableByDay(day));
    }



    @DeleteMapping("/delete/{id}")
    public String deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return "Timetable entry deleted successfully";
    }
}
