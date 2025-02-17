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
    List<TeacherTimetable> findByTeacher_TeacherRegistrationId(String teacherRegistrationId);

    // Find by teacher's registration ID and day
    List<TeacherTimetable> findByTeacher_TeacherRegistrationIdAndDay(String teacherRegistrationId, String day);

    List<TeacherTimetable> findByDay(String day);

}
