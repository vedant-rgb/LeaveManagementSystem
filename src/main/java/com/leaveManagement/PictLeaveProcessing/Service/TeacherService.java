package com.leaveManagement.PictLeaveProcessing.Service;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.TeacherLeave;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public TeacherService(TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    @Secured("ROLE_HOD")
    public TeacherDTO saveTeacher(TeacherDTO teacher) {
        Teacher toBeSaved = modelMapper.map(teacher, Teacher.class);
        if (toBeSaved.getLeave() == null) {
            toBeSaved.setLeave(new TeacherLeave());
        }
        Teacher saved = teacherRepository.save(toBeSaved);
        return modelMapper.map(saved, TeacherDTO.class);
    }

    @Secured({"ROLE_HOD","ROLE_TEACHER"})
    public TeacherDTO getTeacherById() {
        User user = getCurrentuser();
        Teacher teacher = teacherRepository.findByTeacherRegistrationId(user.getTeacherRegistrationId())
                .orElseThrow(() -> new ResourceNotFoundException("No teacher present for id :" + user.getTeacherRegistrationId()));
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    @Secured("ROLE_HOD")
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherDTO.class))
                .collect(Collectors.toList());
    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
