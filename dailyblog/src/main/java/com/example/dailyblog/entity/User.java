package com.example.dailyblog.entity;

import com.example.dailyblog.dto.LoginRequestDto;
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
    @Column(nullable = false, unique = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void checkPassword(LoginRequestDto loginRequestDto){
        if(!this.password.equals(loginRequestDto.getPassword())) throw new IllegalArgumentException("비밀번호가 불일치합니다.");
    }

    public void checkEmail(LoginRequestDto loginRequestDto){
        if(!this.email.equals(loginRequestDto.getEmail())) throw new IllegalArgumentException("아이디가 불일치합니다.");
    }
}
