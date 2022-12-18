package com.example.dailyblog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {
    // 실명
    @Size(min=4,max=10)
    @Pattern(regexp ="^[a-z0-9]*$")
    private String userName;


    //회원 아이디(이메일)
    private String email;

    // 비밀번호
    @Size(min=8,max=15)
    @Pattern(regexp ="^[a-zA-Z0-9]*$")
    private String password;

    private String adminToken = "";


}
