package com.example.dailyblog.dto;

import com.example.dailyblog.entity.Post;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String msg;
    private int statusCode;

    public SignupResponseDto(String msg , int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
