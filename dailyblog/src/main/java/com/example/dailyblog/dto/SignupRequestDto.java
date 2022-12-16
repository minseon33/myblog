package com.example.dailyblog.dto;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
public class SignupRequestDto {
    // 실명
    private String username;


    //회원 아이디(이메일)
    private String email;

    // 비밀번호
    private String password;

    private String adminToken = "";


}
