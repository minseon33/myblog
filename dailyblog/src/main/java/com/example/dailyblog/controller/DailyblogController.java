package com.example.dailyblog.controller;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.security.UserDetailsImpl;
import com.example.dailyblog.service.DailyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyblogController {
    private final DailyblogService dailyblogService;

    //홈화면
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //게시물 등록
    @PostMapping("/posts/dailyposts")
    public PostResponseDto creatPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //권한이랑 같이 서비스로 값 넘겨줌
        return dailyblogService.creatPost(postRequestDto, userDetails.getUsername());
    }

    //전체 게시물 보기
    @GetMapping("/posts/dailyposts")
    public List<PostResponseDto> getPosts() {
        return dailyblogService.getPosts();
    }


    //유저 게시물 수정
    @PutMapping("/posts/dailyposts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 권한에 따라 나누기
        return dailyblogService.userUpdate(id, postRequestDto, userDetails.getUsername());

    }

    //관리자 글 수정
//    @Secured(UserRoleEnum.Authority.ADMIN)
    @PutMapping("/admin/posts/dailyposts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        // 권한에 따라 나누기
        return dailyblogService.adminUpdate(id, postRequestDto);
    }

    //게시물 삭제
    @DeleteMapping("/posts/dailyposts/{id}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 권한에 따라 나누기
        dailyblogService.userDelete(id, userDetails.getUsername());

    }

    //게시물 삭제
//    @Secured(UserRoleEnum.Authority.ADMIN)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/posts/dailyposts/{id}")
    public void deletePost(@PathVariable Long id) {

        // 권한에 따라 나누기
        dailyblogService.adminDelete(id);
    }

    //선택 게시물 보기
    @GetMapping("/posts/dailyposts/{id}")
    public PostResponseDto showPosts(@PathVariable Long id) {
        return dailyblogService.showOnePost(id);
    }



}
