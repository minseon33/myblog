package com.example.dailyblog.controller;

import com.example.dailyblog.dto.LoginRequestDto;
import com.example.dailyblog.dto.LoginResponseDto;
import com.example.dailyblog.dto.SignupRequestDto;
import com.example.dailyblog.dto.SignupResponseDto;
import com.example.dailyblog.entity.User;
import com.example.dailyblog.entity.UserRoleEnum;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.UserRepository;
import com.example.dailyblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private static final String ADMIN_PASSWORD = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


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
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

        //사용자 role 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다. 관리자 가입이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        userService.signup(signupRequestDto,role);
        return new SignupResponseDto("회원가입 완료",200);
    }


    //회원 로그인하기
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto , HttpServletResponse response) {

        String generatedToken = userService.login(loginRequestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,generatedToken);

        return new LoginResponseDto("로그인 완료",200);

    }



}
