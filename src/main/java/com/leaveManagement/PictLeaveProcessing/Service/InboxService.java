package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.Configs.InboxMapper;
import com.leaveManagement.PictLeaveProcessing.DTO.InboxDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.LeaveApplicationDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.AlternateArrangement;
import com.leaveManagement.PictLeaveProcessing.Entity.Inbox;
import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import com.leaveManagement.PictLeaveProcessing.Enums.RequestStatus;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import com.leaveManagement.PictLeaveProcessing.Repository.InboxRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.LeaveApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class InboxService {

    private final ModelMapper modelMapper;
    private final InboxRepository inboxRepository;
    private final InboxMapper inboxMapper;
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public InboxDTO sendMessage(InboxDTO inputMessage) {
        Inbox inbox = inboxMapper.toEntity(inputMessage);
        inbox.setStatus(RequestStatus.PENDING);
        Inbox saved = inboxRepository.save(inbox);
        return modelMapper.map(saved, InboxDTO.class);
    }
    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<InboxDTO> getAllPendingMessagesOfTeacher() {
        User user = getCurrentuser();
        List<Inbox> messages = inboxRepository.getAllReceivedMessagesOfTeacherByRegistrationId(getCurrentuser().getTeacherRegistrationId(), RequestStatus.PENDING);
        return messages.stream()
                .map(msg->modelMapper.map(msg, InboxDTO.class))
                .toList();
    }
    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<InboxDTO> getAllSentMessagesOfTeacher() {
        User user = getCurrentuser();
        List<Inbox> messages = inboxRepository.getAllSentMessagesOfTeacherByRegistrationId(user.getTeacherRegistrationId());
        return messages.stream()
                .map(msg->modelMapper.map(msg, InboxDTO.class))
                .toList();
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public String acceptStatusOfInboxMessageByMsgId(Long msgId) {
        Inbox msg = inboxRepository.findById(msgId).orElseThrow(()->new ResourceNotFoundException("No message found for id : "+msgId));
        msg.setStatus(RequestStatus.APPROVED);
        inboxRepository.save(msg);

        String senderTeacherRegistrationId = msg.getSenderTeacherRegistrationId();
        LeaveApplication leaveApplication = leaveApplicationRepository.getLeaveApplicationByTeacherRegistrationId(senderTeacherRegistrationId);
        List<AlternateArrangement> alternateArrangements = leaveApplication.getAlternateArrangements();

        Optional<AlternateArrangement> first = alternateArrangements.stream()
                .filter(alternateArrangement ->
                        alternateArrangement.getSubstituteTeacher().getTeacherRegistrationId().equals(msg.getReceiverTeacherRegistrationId()) &&
                                alternateArrangement.getDate().equals(msg.getLectureDetails().getDate()) &&
                                alternateArrangement.getStartTime().equals(msg.getLectureDetails().getStartTime()) &&
                                alternateArrangement.getEndTime().equals(msg.getLectureDetails().getEndTime()
                                ))
                .findFirst();
        if(first.isPresent()){
            first.get().setAccepted(true);
        }

        List<AlternateArrangement> acceptedAlternateArrangements = alternateArrangements.stream()
                .filter(AlternateArrangement::getAccepted)
                .toList();

        if(acceptedAlternateArrangements.size() == alternateArrangements.size()){
            leaveApplication.setStatus(ApplicationStatus.PENDING_TO_BE_APPROVED);
        }
        leaveApplicationRepository.save(leaveApplication);
        return "Application Accepted for teacher "+msg.getSenderTeacherRegistrationId()+" for lecture slot of : "
                +msg.getLectureDetails().getStartTime()+" - "+msg.getLectureDetails().getEndTime()
                +" on "+msg.getLectureDetails().getDate()+" for division "+msg.getLectureDetails().getDivision();

    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
