package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // select * from user where username = :username; <- 자동으로 생성 Jpa Query Methods
    public User findByUsername(String username);
}
