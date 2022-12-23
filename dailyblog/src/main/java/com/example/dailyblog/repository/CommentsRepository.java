package com.example.dailyblog.repository;

import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();
//    List<Comment> findAllByPostIdOOrderByPostDesc();
}
