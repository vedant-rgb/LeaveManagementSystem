package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.SignUpRequest;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Service.HodService;
import com.leaveManagement.PictLeaveProcessing.Service.LeaveApplicationService;
import com.leaveManagement.PictLeaveProcessing.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HOD")
public class HodController {

    private final HodService hodService;
    private final AuthService authService;
    private final LeaveApplicationService leaveApplicationService;

    public HodController(HodService hodService, AuthService authService, LeaveApplicationService leaveApplicationService) {
        this.hodService = hodService;
        this.authService = authService;
        this.leaveApplicationService = leaveApplicationService;
    }


    @GetMapping("/pendingLeaves")
    public List<LeaveApplicationDTO> getAllPendingLeaves(){
        return hodService.getLeaveApplicationsByStatus(ApplicationStatus.PENDING_TO_BE_APPROVED);
    }
    @GetMapping("/pendingLeavesById/{leaveId}")
    public ResponseEntity<LeaveApplicationDTO> getLeaveById(@PathVariable Long leaveId) {
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationService.getLeaveById(leaveId);
        return ResponseEntity.ok(leaveApplicationDTO);
    }

    @PostMapping("/acceptLeaveApplication/{applicationId}")
    public ResponseEntity<String> acceptLeaveApplication(@PathVariable Long applicationId){
        return ResponseEntity.ok(hodService.acceptLeaveApplication(applicationId));
    }

    @PostMapping("/rejectLeaveApplication/{applicationId}")
    public ResponseEntity<String> rejectLeaveApplication(@PathVariable Long applicationId){
        return ResponseEntity.ok(hodService.rejectLeaveApplication(applicationId));
    }

    @PostMapping("/createNewTeacher")
    public ResponseEntity<TeacherDTO> createNewTeacher(@RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>(authService.signUp(signUpRequest), HttpStatus.CREATED);
    }
}
