package com.example.dailyblog.exception;

public class TokenNotExistException extends IllegalArgumentException{
    public TokenNotExistException(){
        super("토큰이 존재하지 않습니다.");
    }
}
