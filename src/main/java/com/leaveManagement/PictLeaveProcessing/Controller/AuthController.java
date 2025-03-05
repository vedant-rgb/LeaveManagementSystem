package com.leaveManagement.PictLeaveProcessing.Controller;

import com.leaveManagement.PictLeaveProcessing.DTO.LoginDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.LoginResponseDTO;
import com.leaveManagement.PictLeaveProcessing.DTO.ResetPasswordDTO;
import com.leaveManagement.PictLeaveProcessing.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        String[] tokens = authService.login(loginDTO);

        Cookie cookie = new Cookie("refreshToken",tokens[1]);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (1000L*60*60*24*30*6));
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponseDTO(tokens[0]));
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken(@CookieValue(value = "refreshToken", defaultValue = "null") String refreshToken) {
        if ("null".equals(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token found!");
        }
        return ResponseEntity.ok("Refresh Token Exists: " + refreshToken);
    }


    @GetMapping("/checkIsFirstTimeLogin")
    public ResponseEntity<Boolean> checkIsFirstTimeLogin(){
        return ResponseEntity.ok(authService.checkIsFirstTimeLogin());
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        return ResponseEntity.ok(authService.resetPassword(resetPasswordDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken  = Arrays.stream(request.getCookies())
                .filter(cookie->"refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("Refresh Token not found inside the Cookies"));
        String accessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponseDTO(accessToken));
    }
}