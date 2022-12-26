package com.example.dailyblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    // 유저이름
    @Size(min=4,max=10)
    @Pattern(regexp ="^[a-z0-9]*$")
    private String userName;

    // 비밀번호
    @Size(min=8,max=15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9ㄱ-힣]).+$")
    private String password;

    //관리자 권한
    private boolean admin = false;

    //관리자 패스워드
    private String adminPassword ="";


}
