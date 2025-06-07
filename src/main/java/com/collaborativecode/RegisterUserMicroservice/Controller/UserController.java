package com.collaborativecode.RegisterUserMicroservice.Controller;


import java.util.*;

import com.collaborativecode.RegisterUserMicroservice.Service.UsersService;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import com.collaborativecode.RegisterUserMicroservice.model.UserDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UsersService usersService;


    @GetMapping("/user-info")
    public Map<String , Object> user(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getAttributes();
    }

    @PostMapping("/user-details")
    public ResponseEntity<?> userDetail(@RequestBody User user){
        return usersService.userDetail(user);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok().body("All Users");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        return ResponseEntity.ok().body(usersService.verify(user));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        return usersService.register(userDTO);
    }

}
