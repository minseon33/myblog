package com.example.dailyblog.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String postTitle;
    private String postContents;
    private String clientName;
    private String clientPassword;


}
