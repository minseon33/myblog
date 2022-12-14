package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContents;
    private String userName;

    private LocalDateTime createdAt;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.postNum = post.getPostNum();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
        this.userName = post.getUserName();
        this.createdAt = post.getCreatedAt();
//        this.commentList = new ArrayList<>();
        for(Comment comment : post.getCommentList()){;
            commentList.add(new CommentResponseDto(comment));
        }

//        this.commentList = CommentResponseDto.from();
    }
}

