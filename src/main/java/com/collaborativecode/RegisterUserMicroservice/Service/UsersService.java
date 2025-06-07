package com.collaborativecode.RegisterUserMicroservice.Service;


import com.collaborativecode.RegisterUserMicroservice.Repository.UserRepo;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import com.collaborativecode.RegisterUserMicroservice.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<?> register(UserDTO userDTO){

        if(userRepo.findByEmail(userDTO.getEmail()) != null){
            return ResponseEntity.badRequest().body("Email already in use");
        }

        if(userRepo.findByUsername(userDTO.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username already in use");
        }

        User user = new User();
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        userRepo.save(user);
        return ResponseEntity.ok().body("User Registered Successfully");
    }

    public ResponseEntity<?> userDetail(User u){
        User user = userRepo.findByUsername(u.getUsername());

        if(user == null){
            return ResponseEntity.badRequest().body("No User Found");
        }

        return ResponseEntity.ok().body(user);
    }

    public String verify(User user) {

        User u = userRepo.findByEmail(user.getEmail());

        if(passwordEncoder.matches(user.getPassword()  , u.getPassword())){
            return jwtService.generateToken(user.getEmail());
        }

        return "Failed";
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
