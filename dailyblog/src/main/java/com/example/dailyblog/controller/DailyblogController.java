package com.example.dailyblog.controller;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.entity.User;
import com.example.dailyblog.entity.UserRoleEnum;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.jwt.Token;
import com.example.dailyblog.repository.UserRepository;
import com.example.dailyblog.service.DailyblogService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyblogController {
    private final DailyblogService dailyblogService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final Token token;

    //홈화면
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //게시물 등록
    @PostMapping("/posts/dailypost")
    public Post creatPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {
        //토큰으로 사용자 권한 체크해서 User에 담아줌
        User user = token.checkUserRoleToken(httpServletRequest);
        //권한이랑 같이 서비스로 값 넘겨줌
        return dailyblogService.creatPost(postRequestDto, user);
    }

    //전체 게시물 보기
    @GetMapping("/posts/dailypost")
    public List<Post> getPosts() {
        return dailyblogService.getPosts();
    }

    //게시물 수정
    @PutMapping("/posts/dailypost/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
        User user = token.checkUserRoleToken(httpServletRequest);
        return dailyblogService.update(id, requestDto, user);
    }

    //게시물 삭제
    @DeleteMapping("/posts/dailypost/{id}")
    public void deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {
        User user = token.checkUserRoleToken(httpServletRequest);
        dailyblogService.delete(id, postRequestDto, httpServletRequest);
    }

    //선택 게시물 보기
    @GetMapping("/posts/dailypost/{id}")
    public PostResponseDto showPosts(@PathVariable Long id) {
        return dailyblogService.showOnePost(id);
    }


}
