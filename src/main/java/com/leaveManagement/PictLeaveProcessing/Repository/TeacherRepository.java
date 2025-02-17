package com.leaveManagement.PictLeaveProcessing.Repository;

import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    Optional<Teacher> findByTeacherRegistrationId(String teacherRegistrationId); // Corrected method name

    Optional<Teacher> findByName(String teacherName);

    @Query("""
        SELECT t FROM Teacher t
        WHERE t.teacherRegistrationId NOT IN (
            SELECT tt.teacher.teacherRegistrationId FROM TeacherTimetable tt
            WHERE tt.day = :day
            AND (:startTime < tt.endTime AND :endTime > tt.startTime)
        )
        AND t.teacherRegistrationId <> :excludedTeacherId
    """)
    List<Teacher> findAvailableTeachersExcluding(
            @Param("day") String day,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("excludedTeacherId") String excludedTeacherId
    );
}
