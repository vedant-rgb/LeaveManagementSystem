package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.InboxDTO;
import com.leaveManagement.PictLeaveProcessing.Service.InboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inbox")
@RequiredArgsConstructor
public class InboxController {

    private final InboxService inboxService;

    @PostMapping("/sent-message")
    public InboxDTO sendMessage(@RequestBody InboxDTO inputMessage){
        return inboxService.sendMessage(inputMessage);
    }

    @GetMapping("/getAllPendingMessages/{teacherRegistrationId}")
    public List<InboxDTO>  getAllPendingMessagesOfTeacherByRegistrationId(@PathVariable String teacherRegistrationId){
        return inboxService.getAllPendingMessagesOfTeacherByRegistrationId(teacherRegistrationId);
    }

    @GetMapping("/getAllSentMessages/{teacherRegistrationId}")
    public List<InboxDTO>  getAllSentMessagesOfTeacherByRegistrationId(@PathVariable String teacherRegistrationId){
        return inboxService.getAllSentMessagesOfTeacherByRegistrationId(teacherRegistrationId);
    }
}