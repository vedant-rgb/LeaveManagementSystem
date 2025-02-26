package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherTimetableDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherTimetable;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherTimetableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherTimetableService {

    @Autowired
    private TeacherTimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<TeacherTimetableDTO> getTimetableOfTeacher() {
        User user = getCurrentuser();
        List<TeacherTimetable> timetables = timetableRepository.findByTeacher_TeacherRegistrationId(user.getTeacherRegistrationId());
        return timetables.stream()
                .map(timetable -> modelMapper.map(timetable, TeacherTimetableDTO.class))
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<TeacherTimetableDTO> getTimetableByDay(String day) {
        List<TeacherTimetable> timetables = timetableRepository.findByDay(day);
        return timetables.stream()
                .map(timetable -> modelMapper.map(timetable, TeacherTimetableDTO.class))
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public List<TeacherTimetableDTO> addTimetables(List<TeacherTimetableDTO> dtoList) {
        User user = getCurrentuser();
        if (dtoList.isEmpty()) {
            throw new RuntimeException("Timetable list cannot be empty");
        }

        Optional<Teacher> teacherOptional = teacherRepository.findByTeacherRegistrationId(user.getTeacherRegistrationId());
        if (teacherOptional.isEmpty()) {
            throw new RuntimeException("Teacher not found with ID: " + user.getTeacherRegistrationId());
        }
        Teacher teacher = teacherOptional.get();

        List<TeacherTimetable> timetables = dtoList.stream()
                .map(dto -> {
                    TeacherTimetable timetable = modelMapper.map(dto, TeacherTimetable.class);
                    timetable.setTeacher(teacher);
                    return timetable;
                })
                .collect(Collectors.toList());

        timetableRepository.saveAll(timetables);
        return timetables.stream()
                .map(timetable -> modelMapper.map(timetable, TeacherTimetableDTO.class))
                .collect(Collectors.toList());
    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }
}
