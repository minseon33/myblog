package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Posts;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private final String postTitle;
    private final String postContents;
    private final String clientName;
    private final String clientPassword;


    public PostRequestDto(Posts posts) {
        this.postTitle = posts.getPostTitle();
        this.postContents = posts.getPostContents();
        this.clientName = posts.getClientPassword();
        this.clientPassword = posts.getClientName();
    }
}
