package com.example.dailyblog.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String msg;
    private int statusCode;

    public LoginResponseDto(String msg , int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}
