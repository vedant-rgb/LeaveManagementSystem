package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Service.HodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HOD")
public class HodController {

    private final HodService hodService;

    public HodController(HodService hodService) {
        this.hodService = hodService;
    }

    @GetMapping("/pendingLeaves")
    public List<LeaveApplicationDTO> getAllPendingLeaves(){
        return hodService.getLeaveApplicationsByStatus(ApplicationStatus.PENDING);
    }


    @PutMapping("/update-status/{teacherRegistrationId}")
    public ResponseEntity<String> updateStatusByTeacherRegistrationId(
            @PathVariable String teacherRegistrationId, @RequestParam ApplicationStatus status) {
        int updated = hodService.updateStatusByTeacherRegistrationId(teacherRegistrationId, status);
        return (updated>0) ? ResponseEntity.ok("Leave status updated successfully for Teacher ID: " + teacherRegistrationId)
                : ResponseEntity.badRequest().body("No leave application found for Teacher ID: " + teacherRegistrationId);
    }

}
