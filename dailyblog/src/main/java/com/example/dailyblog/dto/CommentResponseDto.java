package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String commentWriterName;
    private String contents;

    private LocalDateTime commentdate;

    public CommentResponseDto(Comment comment) {
       this.commentId = comment.getCommentId();
       this.commentWriterName = comment.getCommentWriterName();
       this.contents = comment.getCommentContents();
       this.commentdate = comment.getCreatedAt();
    }


}
