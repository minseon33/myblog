package com.example.dailyblog.entity;

import com.example.dailyblog.dto.PostRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postNum;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPassword;


    public Post(PostRequestDto requestDto){
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.userName = requestDto.getUserName();
        this.userPassword = requestDto.getPassword();
    }


    public void update(PostRequestDto requestDto) {
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.userName = requestDto.getUserName();
    }

    public void checkPassword(String password){
        if(!this.userPassword.equals(password)) throw new IllegalArgumentException("비밀번호가 불일치합니다.");
    }


}
