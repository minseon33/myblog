package com.example.dailyblog.service;

import com.example.dailyblog.dto.CommentRequestDto;
import com.example.dailyblog.dto.CommentResponseDto;
import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.CommentsRepository;
import com.example.dailyblog.repository.PostsRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final JwtUtil jwtUtil;
    private final PostsRepository postsRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String userName, Long postNum) {
        //Dto는 한칸만 넘어가야 한다.!!! 아하.>!

        //댓글을 작성할 공간(게시판)을 찾아가는 것이다.
        Post post = postsRepository.findById(postNum).orElseThrow(
                () -> new IllegalArgumentException("등록된 게시글이 없습니다.")
        );

        // 게시판 찾기 완료

        Comment comment = new Comment(userName, commentRequestDto, post);
        post.addComment(comment);

//        //게시물 작성하기
//        post.addComment(comment);
        commentsRepository.save(comment);
        postsRepository.save(post);

        return new CommentResponseDto(comment);
    }
}
