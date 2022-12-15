package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContents;
    private String userName;

    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.postNum = post.getPostNum();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
        this.userName = post.getUserName();
        this.createdAt = post.getCreatedAt();
    }
}

