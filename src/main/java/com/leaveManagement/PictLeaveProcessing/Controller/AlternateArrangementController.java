package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.AlternateArrangementDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.AlternateArrangement;
import com.leaveManagement.PictLeaveProcessing.Service.AlternateArrangementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alternate-arrangements")
@RequiredArgsConstructor
public class AlternateArrangementController {

    private final AlternateArrangementService alternateArrangementService;

    @GetMapping("/{teacherRegistrationId}")
    public List<AlternateArrangementDTO> getAllottedArrangementBySubstituteTeacherId(@PathVariable String teacherRegistrationId){
        return alternateArrangementService.getAllottedArrangementForSubstituteTeacher(teacherRegistrationId);
    }

}
