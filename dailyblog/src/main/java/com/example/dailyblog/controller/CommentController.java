package com.example.dailyblog.controller;


import com.example.dailyblog.dto.CommentRequestDto;
import com.example.dailyblog.dto.CommentResponseDto;
import com.example.dailyblog.entity.UserRoleEnum;
import com.example.dailyblog.security.UserDetailsImpl;
import com.example.dailyblog.service.AuthenticationService;
import com.example.dailyblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postComments")
public class CommentController {
    private final CommentService commentService;



    //댓글창 보여주기
    @GetMapping("/")
    public ModelAndView commentHome() {
        return new ModelAndView("commentHome");
    }



    //댓글 등록
    @PostMapping("/{postNum}/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postNum, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        //서비스로 Dto 넘겨줌
        return commentService.createComment(commentRequestDto, userDetails.getUsername(), postNum);
    }



    //댓글 수정
    @PutMapping("/{postNum}/comment/{commentNum}")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postNum, @PathVariable Long commentNum, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.userUpdateComment(commentRequestDto, userDetails.getUsername(), postNum, commentNum);
    }



    //댓글 삭제
    @DeleteMapping("/{postNum}/comment/{commentNum}")
    public void CommentDelet(@PathVariable Long postNum, @PathVariable Long commentNum, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.userCommentDelet(postNum, commentNum, userDetails.getUsername());

    }


    // 관리자 댓글 삭제
//    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("admin/{postNum}/comment/{commentNum}")
    public void CommentDelet(@PathVariable Long postNum, @PathVariable Long commentNum) {

        // 토큰 값 꺼내기
        commentService.adminCommentDelet(postNum, commentNum);

    }


}
