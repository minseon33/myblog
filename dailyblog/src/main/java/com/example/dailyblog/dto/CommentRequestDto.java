package com.example.dailyblog.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {

    private Long postNum;
    private String contents;

}
