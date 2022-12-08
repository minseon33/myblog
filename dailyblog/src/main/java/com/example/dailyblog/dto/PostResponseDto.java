package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Timestamped;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContents;
    private String clientName;
    private String clientPassword;
    private Timestamped Timestamped;

}
