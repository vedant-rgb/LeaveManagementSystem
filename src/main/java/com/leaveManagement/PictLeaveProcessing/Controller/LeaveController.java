package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final TeacherLeaveService teacherLeaveService;

    @PostMapping("/apply")
    public ResponseEntity<?> submitLeaveApplication(@RequestBody LeaveApplicationDTO leaveApplicationDTO){
        teacherLeaveService.submitLeaveApplication(leaveApplicationDTO);
        return null;
    }

}
