package com.leaveManagement.PictLeaveProcessing.Repository;

import com.leaveManagement.PictLeaveProcessing.Entity.AlternateArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlternateArrangementRepository extends JpaRepository<AlternateArrangement,Long> {

    @Query("SELECT aa FROM AlternateArrangement aa WHERE aa.substituteTeacher.teacherRegistrationId=:substituteTeacherId")
    List<AlternateArrangement> findAlternateArrangementBySubstituteTeacherRegistrationId(String substituteTeacherId);
}
