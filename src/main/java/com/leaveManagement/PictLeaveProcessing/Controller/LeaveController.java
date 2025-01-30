package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherLeaveDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping("/leaveRemaining/{teacherId}")
    public ResponseEntity<TeacherLeaveDTO> getRemainingLeaves(@PathVariable String teacherId){
        TeacherLeaveDTO leavesRemaining = leaveService.findLeaveRemaining(teacherId);
        return ResponseEntity.ok(leavesRemaining);
    }

    @PostMapping("/takeLeave")
    public ResponseEntity<String> takeLeave(@RequestParam String teacherId, @RequestParam String leaveType) {
        String message= leaveService.takeLeave(teacherId, leaveType);
        return ResponseEntity.ok(message);
    }

}
