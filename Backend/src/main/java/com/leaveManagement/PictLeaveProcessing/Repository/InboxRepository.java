package com.leaveManagement.PictLeaveProcessing.Repository;

import com.leaveManagement.PictLeaveProcessing.Entity.Inbox;
import com.leaveManagement.PictLeaveProcessing.Enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface InboxRepository extends JpaRepository<Inbox,Long> {

    @Query("SELECT i FROM Inbox i WHERE i.status = :status AND i.receiverTeacherRegistrationId = :teacherRegistrationId")
    List<Inbox> getAllReceivedMessagesOfTeacherByRegistrationId(@Param("teacherRegistrationId") String teacherRegistrationId,
                                                               @Param("status") RequestStatus status);

    @Query("SELECT i FROM Inbox i WHERE i.senderTeacherRegistrationId = :teacherRegistrationId")
    List<Inbox> getAllSentMessagesOfTeacherByRegistrationId(@Param("teacherRegistrationId") String teacherRegistrationId);


}
