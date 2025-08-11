package com.collaborativecode.RegisterUserMicroservice.Service;


import com.collaborativecode.RegisterUserMicroservice.Repository.UserRepository;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUsersDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    public CustomUsersDetailService(){

    }

    public User loadUserByEmail(String email) throws  Exception{
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
