package com.leaveManagement.PictLeaveProcessing.Repository;

import com.leaveManagement.PictLeaveProcessing.Entity.LeaveApplication;
import com.leaveManagement.PictLeaveProcessing.Enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    LeaveApplication getLeaveApplicationByTeacherRegistrationId(String teacherRegistrationId);


    @Query("SELECT la FROM LeaveApplication la WHERE la.status=:status")
    List<LeaveApplication> getLeaveApplicationByStatus(ApplicationStatus status);


}
