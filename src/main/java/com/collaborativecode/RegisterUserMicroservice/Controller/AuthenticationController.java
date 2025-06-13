package com.collaborativecode.RegisterUserMicroservice.Controller;

import com.collaborativecode.RegisterUserMicroservice.Service.AuthenticationService;
import com.collaborativecode.RegisterUserMicroservice.Service.JwtService;
import com.collaborativecode.RegisterUserMicroservice.dto.LoginUserDto;
import com.collaborativecode.RegisterUserMicroservice.dto.UserDTO;
import com.collaborativecode.RegisterUserMicroservice.dto.VerifyUserDto;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import com.collaborativecode.RegisterUserMicroservice.responses.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken , jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        return authenticationService.signup(userDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto){

        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok().body("Account Verified Successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Account Not Verified");
        }
    }
}
