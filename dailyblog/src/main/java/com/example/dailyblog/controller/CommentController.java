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

        //토큰에서 유저네임(=작성자) 뽑아서 넘겨줌
        String userName = tokenAuthenticationService.takeUserName(token);

        //서비스로 Dto 넘겨줌
        return commentService.createComment(commentRequestDto,userName,postNum);
    }

    @PutMapping("/post/{postNum}/comment/{commentNum}")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto commentRequestDto,@PathVariable Long postNum,@PathVariable Long commentNum ,HttpServletRequest httpServletRequest){
        // 토큰 값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        tokenAuthenticationService.tokenVerification(token);

        //토큰에서 유저네임(=작성자) 뽑아서 userName에 저장하기
        String userName = tokenAuthenticationService.takeUserName(token);

        return commentService.userUpdateComment(commentRequestDto,userName,postNum,commentNum);
    }


    @DeleteMapping("/post/{postNum}/comment/{commentNum}")
    public void CommentDelet(@PathVariable Long postNum ,@PathVariable Long commentNum, HttpServletRequest httpServletRequest){
        // 토큰 값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        tokenAuthenticationService.tokenVerification(token);

        //토큰에서 유저네임(=작성자) 뽑아서 userName에 저장하기

        String userName = tokenAuthenticationService.takeUserName(token);

        String role = tokenAuthenticationService.takeRole(token);

        if(role.equals("ADMIN")){
            commentService.adminCommentDelet(postNum,commentNum);
        }else {
            commentService.userCommentDelet(postNum,commentNum,userName);
        }


    }





}
