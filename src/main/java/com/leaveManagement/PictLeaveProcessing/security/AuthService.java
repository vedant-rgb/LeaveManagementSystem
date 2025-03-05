package com.leaveManagement.PictLeaveProcessing.security;

import com.leaveManagement.PictLeaveProcessing.DTO.LoginDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.ResetPasswordDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.SignUpRequest;
import com.leaveManagement.PictLeaveProcessing.DTO.TeacherDTO;
import com.leaveManagement.PictLeaveProcessing.Entity.Teacher;
import com.leaveManagement.PictLeaveProcessing.Entity.User;
import com.leaveManagement.PictLeaveProcessing.Enums.Role;
import com.leaveManagement.PictLeaveProcessing.Exceptions.ResourceNotFoundException;
import com.leaveManagement.PictLeaveProcessing.Repository.TeacherRepository;
import com.leaveManagement.PictLeaveProcessing.Repository.UserRepository;
import com.leaveManagement.PictLeaveProcessing.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherService teacherService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Transactional
    @Secured("ROLE_HOD")
    public TeacherDTO signUp(SignUpRequest signUpRequest){
        Optional<User> user =userRepository.findByTeacherRegistrationId(signUpRequest.getTeacherRegistrationId());
        if(user.isPresent()){
            throw new RuntimeException("Teacher already present with Registration ID : "+signUpRequest.getTeacherRegistrationId());
        }
        TeacherDTO teacherDTO = modelMapper.map(signUpRequest, TeacherDTO.class);
        TeacherDTO savedTeacherDTO = teacherService.saveTeacher(teacherDTO);
        Teacher savedTeacher = modelMapper.map(savedTeacherDTO, Teacher.class);

        User newUser = new User(
                signUpRequest.getTeacherRegistrationId(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName()
        );
        newUser.setIsFirstLogin(true);
        newUser.setTeacher(savedTeacher);
        newUser.setRoles(Set.of(Role.TEACHER));

        newUser =userRepository.save(newUser);
        return savedTeacherDTO;
    }

    public String[] login(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getTeacherRegistrationId(),loginDTO.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String[] arr = new String[2];
        arr[0] = jwtService.generateAccessToken(user);
        arr[1] = jwtService.generateRefreshToken(user);
        return arr;
    }


    public Boolean checkIsFirstTimeLogin() {
        User currUser = getCurrentuser();
        User user = userRepository.findByTeacherRegistrationId(currUser.getTeacherRegistrationId()).orElseThrow(() -> new ResourceNotFoundException("Teacher not found for Registration id : " + currUser.getTeacherRegistrationId()));
        if(user.getIsFirstLogin().equals(true)){
            return true;
        }
        return false;
    }

    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User currUser = getCurrentuser();
        User user = userRepository.findByTeacherRegistrationId(currUser.getTeacherRegistrationId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found for Registration id : " + currUser.getTeacherRegistrationId()));
        String encoded = passwordEncoder.encode(resetPasswordDTO.getOldPassword());
        if(!passwordEncoder.matches(resetPasswordDTO.getOldPassword(), currUser.getPassword())){
            throw new RuntimeException("Old password is wrong");
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        user.setIsFirstLogin(false);
        User savedUser = userRepository.save(user);
        return "Password updated successfully";
    }

    public String refreshToken(String refreshToken){
        Long id = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id :"+id));
        return jwtService.generateAccessToken(user);
    }

    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
