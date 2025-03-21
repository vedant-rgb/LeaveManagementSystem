package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Service.LeaveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveApplication")
@RequiredArgsConstructor
public class LeaveApplicationController {

    @Autowired
    private  LeaveApplicationService leaveApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForLeave(@RequestBody LeaveApplicationDTO leaveApplicationDTO) {
        leaveApplicationService.applyForLeave(leaveApplicationDTO);
        return ResponseEntity.ok("Leave application submitted successfully.");
    }

    @GetMapping("/leaveId/{leaveId}")
    public ResponseEntity<LeaveApplicationDTO> getLeaveById(@PathVariable Long leaveId) {
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationService.getLeaveById(leaveId);
        return ResponseEntity.ok(leaveApplicationDTO);
    }

    @GetMapping
    public ResponseEntity<LeaveApplicationDTO> getLeaveByTeacherRegistrationId() {
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationService.getLeavesOfTeacher();
        return ResponseEntity.ok(leaveApplicationDTO);
    }
    @GetMapping("/all")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllLeaves() {
        return ResponseEntity.ok(leaveApplicationService.getAllLeaves());
    }

    @DeleteMapping("/{leaveId}")
    public ResponseEntity<String> cancelLeave(@PathVariable Long leaveId) {
        leaveApplicationService.cancelLeave(leaveId);
        return ResponseEntity.ok("Leave application canceled successfully.");
    }
}
