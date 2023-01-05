package com.example.dailyblog.repository;

import com.example.dailyblog.entity.Post;
import com.example.dailyblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    Optional<User> findByUsername(String username);

}
