package com.collaborativecode.RegisterUserMicroservice.Controller;


import java.util.*;

import com.collaborativecode.RegisterUserMicroservice.Repository.UserRepository;
import com.collaborativecode.RegisterUserMicroservice.Service.UsersService;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import com.collaborativecode.RegisterUserMicroservice.responses.UserResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);
    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user-info")
    public Map<String , Object> user(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getAttributes();
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<Map<String , Boolean>> checkUsername(@RequestParam(name = "username") String username){

        Map<String, Boolean> h = usersService.checkUsername(username);
        return ResponseEntity.ok(h);

    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {


            Object principal = authentication.getPrincipal();
            String username;
            if (principal instanceof User user){
                username = user.getEmail();
            } else if (principal instanceof  String stringPrincipal){
                username = stringPrincipal;
            }else{
                username = authentication.getName();
            }

            User user = userRepository.findByEmail(username)
                    .orElseThrow(()->new UsernameNotFoundException("Username not found"));


            UserResponse response = new UserResponse(user.getId().toString() , user.getUsername() ,  user.getEmail() , user.getProjects() , user.isEnabled());
            response.isAccountNonExpired(user.isAccountNonExpired());
            response.isAccountNonLocked(user.isAccountNonLocked());
            response.isCredentialsNonExpired(user.isCredentialsNonExpired());

            return ResponseEntity.ok().body(response);
        }else{
            System.out.println("Authentication is null");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers(){
        List<User> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getAllProjectsOfUser")
    public ResponseEntity<List> getAllProjectsOfUser(@RequestParam(name = "user_id") Long user_id){
        return usersService.getAllProjectOfUser(user_id);
    }


}
