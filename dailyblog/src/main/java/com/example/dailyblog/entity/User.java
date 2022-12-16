package com.example.dailyblog.entity;

import com.example.dailyblog.dto.LoginRequestDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    public User(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.email = signupRequestDto.getEmail();
    }

    public void checkPassword(LoginRequestDto loginRequestDto){
        if(!this.password.equals(loginRequestDto.getPassword())) throw new IllegalArgumentException("비밀번호가 불일치합니다.");
    }

    public void checkUserName(PostRequestDto postRequestDto){
        if(!this.username.equals(postRequestDto.getUserName())) throw new IllegalArgumentException("ID가 불일치합니다.");
    }

}
