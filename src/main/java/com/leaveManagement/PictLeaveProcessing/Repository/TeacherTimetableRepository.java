package com.leaveManagement.PictLeaveProcessing.Repository;

import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TeacherTimetableRepository extends JpaRepository<TeacherTimetable, Long> {

    // Fetch all timetables for a given teacher ID
    List<TeacherTimetable> findByTeacher_TeacherId(String teacherId);

    // Fetch all timetables for a specific day
    List<TeacherTimetable> findByDay(String day);

    // Delete all timetable entries for a given teacher ID
    void deleteByTeacher_TeacherId(String teacherId);

    // Fetch free teachers who don't have lectures at a given day and time slot
    @Query("SELECT t FROM Teacher t WHERE t.teacherId NOT IN " +
            "(SELECT tt.teacher.teacherId FROM TeacherTimetable tt WHERE tt.day = :day " +
            "AND ((tt.startTime <= :startTime AND tt.endTime > :startTime) OR " +
            "(tt.startTime < :endTime AND tt.endTime >= :endTime))) " +
            "AND t.teacherId <> :excludedTeacherId")
    List<Teacher> findAvailableTeachersExcluding(String day, LocalTime startTime, LocalTime endTime, String excludedTeacherId);

}
