package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Posts;
import com.example.dailyblog.entity.Timestamped;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContents;
    private String clientName;
    private String clientPassword;
    private LocalDateTime createdAt;

    public PostResponseDto(Posts posts) {
        this.postNum = posts.getPostNum();
        this.postTitle = posts.getPostTitle();
        this.postContents = posts.getPostContents();
        this.clientName = posts.getClientName();
        this.clientPassword = posts.getClientPassword();
        this.createdAt = posts.getCreatedAt();
    }
}
