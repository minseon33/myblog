package com.example.dailyblog.entity;

import com.example.dailyblog.dto.LoginRequestDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRoleEnum role;


    public User(SignupRequestDto signupRequestDto , UserRoleEnum role) {
        this.username = signupRequestDto.getUserName();
        this.password = signupRequestDto.getPassword();
        this.role = role;
    }

    public void checkPassword(String userpassword){
        if(!this.password.equals(userpassword)) throw new IllegalArgumentException("비밀번호가 불일치합니다.");
    }



}
