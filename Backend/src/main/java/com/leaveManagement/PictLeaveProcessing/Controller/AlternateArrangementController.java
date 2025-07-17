package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.AlternateArrangementDTO;
import com.leaveManagement.PictLeaveProcessing.Service.AlternateArrangementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alternate-arrangements")
@RequiredArgsConstructor
public class AlternateArrangementController {

    private final AlternateArrangementService alternateArrangementService;

    @GetMapping
    public List<AlternateArrangementDTO> getAllottedArrangementOfTeacher(){
        return alternateArrangementService.getAllottedArrangementForSubstituteTeacher();
    }

}
