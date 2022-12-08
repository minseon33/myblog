package com.example.dailyblog.repository;

import com.example.dailyblog.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    List<Posts> findAllByOrderByModifiedAtDesc();
}
