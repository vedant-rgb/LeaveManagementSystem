package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.InboxDTO;
import com.leaveManagement.PictLeaveProcessing.Service.InboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getAllPendingMessages")
    public List<InboxDTO> getAllPendingMessagesOfTeacher() {
        return inboxService.getAllPendingMessagesOfTeacher();
    }

    @GetMapping("/getAllSentMessages")
    public List<InboxDTO> getAllSentMessagesOfTeacher() {
        return inboxService.getAllSentMessagesOfTeacher();
    }

    @PutMapping("/acceptRequest/{msgId}")
    public ResponseEntity<String> updateStatusByTeacherRegistrationId(@PathVariable Long msgId) {
             return ResponseEntity.ok(inboxService.acceptStatusOfInboxMessageByMsgId(msgId));
    }
}