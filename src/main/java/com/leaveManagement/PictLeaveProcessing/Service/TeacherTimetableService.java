package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherTimetableDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherTimetable;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherTimetableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherTimetableService {

    @Autowired
    private TeacherTimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TeacherTimetableDTO> getTimetableByTeacherId(String teacherId) {
        List<TeacherTimetable> timetables = timetableRepository.findByTeacher_TeacherRegistrationId(teacherId);
        return timetables.stream()
                .map(timetable -> modelMapper.map(timetable, TeacherTimetableDTO.class))
                .collect(Collectors.toList());
    }

    public List<TeacherTimetableDTO> getTimetableByDay(String day) {
        List<TeacherTimetable> timetables = timetableRepository.findByDay(day);
        return timetables.stream()
                .map(timetable -> modelMapper.map(timetable, TeacherTimetableDTO.class))
                .collect(Collectors.toList());
    }

    public List<TeacherTimetableDTO> addTimetables(String teacherId, List<TeacherTimetableDTO> dtoList) {
        if (dtoList.isEmpty()) {
            throw new RuntimeException("Timetable list cannot be empty");
        }

        Optional<Teacher> teacherOptional = teacherRepository.findByTeacherRegistrationId(teacherId);
        if (teacherOptional.isEmpty()) {
            throw new RuntimeException("Teacher not found with ID: " + teacherId);
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

    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }
}
