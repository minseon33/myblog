package com.example.dailyblog.entity;

import com.example.dailyblog.dto.PostRequestDto;

import com.example.dailyblog.dto.PostResponseDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Posts extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postNum;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPassword;


    public Posts(PostRequestDto requestDto){
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.clientName = requestDto.getClientName();
        this.clientPassword = requestDto.getClientPassword();
    }


    public void update(PostRequestDto requestDto) {
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.clientName = requestDto.getClientName();
    }

    public void checkPassword(String password){
        if(!this.clientPassword.equals(password)) throw new IllegalArgumentException("비밀번호가 불일치합니다.");
    }
}
