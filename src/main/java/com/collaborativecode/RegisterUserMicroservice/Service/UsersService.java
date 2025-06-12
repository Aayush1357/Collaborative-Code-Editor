package com.collaborativecode.RegisterUserMicroservice.Service;


import com.collaborativecode.RegisterUserMicroservice.Repository.UserRepository;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepo;


    public ResponseEntity<?> userDetail(User u){
        Optional<User> user = userRepo.findByUsername(u.getUsername());

        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("No User Found");
        }

        return ResponseEntity.ok().body(user);
    }


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
