package com.example.dailyblog.controller;

import com.example.dailyblog.dto.LoginRequestDto;
import com.example.dailyblog.dto.SignupRequestDto;
import com.example.dailyblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
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
    public String signup(@RequestBody SignupRequestDto signupRequestDto ) {
        userService.signup(signupRequestDto);
        return "redirect:/api/user/login";
    }

    //회원 로그인하기
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto , HttpServletResponse response) {
        userService.login(loginRequestDto,response);
        return "redirect:/api/shop";
    }



}
