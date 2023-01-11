package com.example.dailyblog.repository;

import com.example.dailyblog.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Optional<CommentLike> findByUserName(String userName);
    int countByCommentNum(Long commentNum);

}
