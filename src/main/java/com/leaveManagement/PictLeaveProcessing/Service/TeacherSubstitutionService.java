package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherNameDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherTimetable;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherTimetableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherSubstitutionService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherTimetableRepository timetableRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get free teachers for a given date and time slot
    public List<TeacherNameDTO> getAvailableTeachers(LocalDate date, LocalTime startTime, LocalTime endTime, String excludedTeacherId) {
        String day = getDayOfWeek(date);
        List<Teacher> teacherList = timetableRepository.findAvailableTeachersExcluding(day, startTime, endTime, excludedTeacherId);
        return teacherList.stream()
                .map(teacher -> modelMapper.map(teacher,TeacherNameDTO.class))
                .toList();
    }


    // Assign a substitute teacher to a lecture
    public String assignSubstituteTeacher(String absentTeacherId, String substituteTeacherId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String day = getDayOfWeek(date);

        // Get the timetable entry for the absent teacher
        Optional<TeacherTimetable> timetableEntry = timetableRepository.findByTeacher_TeacherId(absentTeacherId)
                .stream()
                .filter(tt -> tt.getDay().equalsIgnoreCase(day) && tt.getStartTime().equals(startTime))
                .findFirst();

        if (timetableEntry.isPresent()) {
            // Fetch substitute teacher
            Optional<Teacher> substituteTeacher = teacherRepository.findByTeacherId(substituteTeacherId);
            if (substituteTeacher.isEmpty()) {
                return "Substitute teacher not found.";
            }

            // Assign the substitute teacher
            TeacherTimetable timetable = timetableEntry.get();
            timetable.setTeacher(substituteTeacher.get());
            timetableRepository.save(timetable);

            return "Substitute teacher assigned successfully.";
        } else {
            return "No matching lecture found for the absent teacher.";
        }
    }

    // Utility method to get the day of the week from a LocalDate
    private String getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().name();
    }
}
