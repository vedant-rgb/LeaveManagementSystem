package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.InboxMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.InboxDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Inbox;
import com.leaveManagement.PictLeaveProcessing.Enums.RequestStatus;
import com.leaveManagement.PictLeaveProcessing.Repository.InboxRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InboxService {

    private final ModelMapper modelMapper;
    private final InboxRepository inboxRepository;
    private final InboxMapper inboxMapper;

    public InboxDTO sendMessage(InboxDTO inputMessage) {
        Inbox inbox = inboxMapper.toEntity(inputMessage);
        inbox.setStatus(RequestStatus.PENDING);
        Inbox saved = inboxRepository.save(inbox);
        return modelMapper.map(saved, InboxDTO.class);
    }

    public List<InboxDTO> getAllPendingMessagesOfTeacherByRegistrationId(String teacherRegistrationId) {
        List<Inbox> messages = inboxRepository.getAllReceivedMessagesOfTeacherByRegistrationId(teacherRegistrationId, RequestStatus.PENDING);
        return messages.stream()
                .map(msg->modelMapper.map(msg, InboxDTO.class))
                .toList();
    }

    public List<InboxDTO> getAllSentMessagesOfTeacherByRegistrationId(String teacherRegistrationId) {
        List<Inbox> messages = inboxRepository.getAllSentMessagesOfTeacherByRegistrationId(teacherRegistrationId);
        return messages.stream()
                .map(msg->modelMapper.map(msg, InboxDTO.class))
                .toList();
    }

}
