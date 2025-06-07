package com.collaborativecode.RegisterUserMicroservice.Repository;

import com.collaborativecode.RegisterUserMicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByUsername(String username);
}
