package com.leaveManagement.PictLeaveProcessing.security;

import com.leaveManagement.PictLeaveProcessing.DTO.*;
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
import java.util.stream.Collectors;

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

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getTeacherRegistrationId(), loginDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        Set<String> roles = user.getRoles().stream()
                .map(role->role.name())  // Assuming Role has a `getName()` method
                .collect(Collectors.toSet());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken, roles);
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

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long id = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponseDTO(accessToken, refreshToken, user.getRoles()
                .stream()
                .map(role -> role.name()) // Assuming Role has a getName() method
                .collect(Collectors.toSet()));
    }


    private User getCurrentuser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
