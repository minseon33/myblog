package com.example.dailyblog.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {

    private Long commentNum;
    private String commentWriterName;
    private LocalDateTime createdAt;
    private String contents;

}
