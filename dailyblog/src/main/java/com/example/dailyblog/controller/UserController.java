package com.example.dailyblog.controller;

import com.example.dailyblog.dto.LoginRequestDto;
import com.example.dailyblog.dto.LoginResponseDto;
import com.example.dailyblog.dto.SignupRequestDto;
import com.example.dailyblog.dto.SignupResponseDto;
import com.example.dailyblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입 페이지 보여주기
    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    //로그인 페이지 보여주기
    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    //회원가입 등록하기
    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto ) {
        return userService.signup(signupRequestDto);
    }

    //회원 로그인하기
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto , HttpServletResponse response) {
        return userService.login(loginRequestDto,response);
    }



}
