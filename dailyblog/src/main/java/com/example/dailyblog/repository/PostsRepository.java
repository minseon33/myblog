package com.example.dailyblog.repository;

import com.example.dailyblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBulletinDesc();
}
