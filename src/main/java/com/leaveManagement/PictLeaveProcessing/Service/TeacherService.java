package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherLeave;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public TeacherService(TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    public TeacherDTO saveTeacher(TeacherDTO teacher) {
        Teacher toBeSaved = modelMapper.map(teacher, Teacher.class);
        if (toBeSaved.getLeave() == null) {
            toBeSaved.setLeave(new TeacherLeave());
        }
        Teacher savedTeacher = teacherRepository.save(toBeSaved);
        return modelMapper.map(savedTeacher, TeacherDTO.class);
    }

    public TeacherDTO getTeacherById(String teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("No teacher present for id :" + teacherId));
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherDTO.class))
                .collect(Collectors.toList());
    }

}
