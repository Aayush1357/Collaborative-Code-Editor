package com.collaborativecode.RegisterUserMicroservice.Service;


import com.collaborativecode.RegisterUserMicroservice.Repository.UserRepository;
import com.collaborativecode.RegisterUserMicroservice.model.Project;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> userDetail(User u){
        Optional<User> user = userRepo.findByUsername(u.getUsername());

        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("No User Found");
        }

        return ResponseEntity.ok().body(user);
    }


    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }


    public Map<String , Boolean> checkUsername(String username) {

        Optional<User> user = userRepo.findByUsername(username);

        boolean ex = user.isPresent();

        HashMap<String , Boolean> h = new HashMap<>();
        h.put("exists" , ex);
        return h;
    }

    public ResponseEntity<List> getAllProjectOfUser(Long user_id){
        Optional<User> optUser = userRepository.findById(user_id);

        if (optUser.isPresent()){
            User user = optUser.get();
            List<Project> projects = user.getProjects();

            return ResponseEntity.ok().body(projects);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
