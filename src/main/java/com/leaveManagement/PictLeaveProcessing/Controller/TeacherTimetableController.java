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

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TeacherTimetableDTO>> getTimetableByTeacherId(@PathVariable String teacherId) {
        return ResponseEntity.ok(timetableService.getTimetableByTeacherId(teacherId));
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<TeacherTimetableDTO>> getTimetableByDay(@PathVariable String day) {
        return ResponseEntity.ok(timetableService.getTimetableByDay(day));
    }

    @PostMapping("/add/{teacherId}")
    public ResponseEntity<List<TeacherTimetableDTO>> addTimetable(
            @PathVariable String teacherId,
            @RequestBody List<TeacherTimetableDTO> timetableDTOs) {
        return ResponseEntity.ok(timetableService.addTimetables(teacherId, timetableDTOs));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return "Timetable entry deleted successfully";
    }
}
