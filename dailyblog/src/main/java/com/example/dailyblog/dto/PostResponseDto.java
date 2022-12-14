package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContents;
    private String clientName;

    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.postNum = post.getPostNum();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
        this.clientName = post.getClientName();
        this.createdAt = post.getCreatedAt();
    }
}

