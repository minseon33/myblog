package com.example.dailyblog.controller;


import com.example.dailyblog.dto.CommentRequestDto;
import com.example.dailyblog.dto.CommentResponseDto;
import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.PostsRepository;
import com.example.dailyblog.repository.UserRepository;
import com.example.dailyblog.service.CommentService;
import com.example.dailyblog.service.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postComments")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final PostsRepository postsRepository;


    //댓글창 보여주기
    @GetMapping("/")
    public ModelAndView commentHome() {
        return new ModelAndView("commentHome");
    }

    @PostMapping("/post/{postNum}/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postNum , HttpServletRequest httpServletRequest) {

        //httpServletRequest 에서 토큰값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        tokenAuthenticationService.tokenVerification(token);

        //게시물 확인하기
        Post post = postsRepository.findById(postNum).orElseThrow(
                ()->new IllegalArgumentException("등록된 게시글이 없습니다.")
        );
        //서비스로 Dto 넘겨줌
        return commentService.createComment(commentRequestDto,post);
    }





}
