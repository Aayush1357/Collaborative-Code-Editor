package com.collaborativecode.RegisterUserMicroservice.Controller;


import java.util.*;

import com.collaborativecode.RegisterUserMicroservice.Service.UsersService;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;


    @GetMapping("/user-info")
    public Map<String , Object> user(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getAttributes();
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers(){
        List<User> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
