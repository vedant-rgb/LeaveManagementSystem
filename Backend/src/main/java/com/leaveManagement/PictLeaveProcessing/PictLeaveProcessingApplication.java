package com.leaveManagement.PictLeaveProcessing;

import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Enums.Role;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.UserRepository;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherService;
import com.leaveManagement.PictLeaveProcessing.security.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class PictLeaveProcessingApplication {

	private final UserRepository userRepository;
	private final TeacherRepository teacherRepository;
	private final PasswordEncoder passwordEncoder;

	public PictLeaveProcessingApplication(UserRepository userRepository,
                                          TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public static void main(String[] args) {
		SpringApplication.run(PictLeaveProcessingApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Teacher teacher = new Teacher(
//				"C2K221121",
//				"Vedant Ghumare",
//				"Dr."
//				"JAVA",
//				"Computer Engineering"
//		);
//		Teacher saved = teacherRepository.save(teacher);
//		User HOD = new User("C2K221121",
//					"ghumarevedant2525@gmail.com",
//				passwordEncoder.encode("password"),
//				"Vedant Ghumare"
//				);
//		HOD.setRoles(Set.of(Role.HOD));
//		HOD.setTeacher(saved);
//		userRepository.save(HOD);
//	}
}