package com.collaborativecode.RegisterUserMicroservice.Controller;

import com.collaborativecode.RegisterUserMicroservice.Service.AuthenticationService;
import com.collaborativecode.RegisterUserMicroservice.Service.JwtService;
import com.collaborativecode.RegisterUserMicroservice.dto.LoginUserRequest;
import com.collaborativecode.RegisterUserMicroservice.dto.UserDTO;
import com.collaborativecode.RegisterUserMicroservice.dto.VerifyUserRequest;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import com.collaborativecode.RegisterUserMicroservice.responses.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserRequest loginUserDto  , HttpServletResponse response ) throws Exception {

        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            System.out.println(authenticatedUser.getUsername() + " " + authenticatedUser.getEmail());
            if (authenticatedUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User is not enabled"));

            String jwtToken = jwtService.generateToken(authenticatedUser);


            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge((int)Duration.ofDays(1).getSeconds());
            response.addCookie(cookie);

            return ResponseEntity.ok("Login Successful");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return authenticationService.signup(userDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserRequest verifyUserDto) {

        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok().body("Account Verified Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Account Not Verified");
        }
    }
}
