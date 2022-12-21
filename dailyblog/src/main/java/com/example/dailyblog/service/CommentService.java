package com.example.dailyblog.service;

import com.example.dailyblog.dto.CommentRequestDto;
import com.example.dailyblog.dto.CommentResponseDto;
import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.CommentsRepository;
import com.example.dailyblog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final JwtUtil jwtUtil;
    private final PostsRepository postsRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Post post) {

        Comment comment = new Comment(commentRequestDto,post);

//        //게시물 작성하기
//        post.addComment(comment);
        commentsRepository.save(comment);
        return new CommentResponseDto(comment);
    }
}
