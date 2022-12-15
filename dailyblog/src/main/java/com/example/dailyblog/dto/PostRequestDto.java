package com.example.dailyblog.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String postTitle;
    private String postContents;
    private String userName;
    private String userPassword;


//    public PostRequestDto(Posts posts) {
//        this.postTitle = posts.getPostTitle();
//        this.postContents = posts.getPostContents();
//        this.clientName = posts.getClientName();
//        this.clientPassword = posts.getClientPassword();
//    }
// 얜 왜 final 하면 안되는가...
}
