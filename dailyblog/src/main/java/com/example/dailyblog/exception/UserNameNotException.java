package com.example.dailyblog.exception;

public class UserNameNotException extends IllegalArgumentException{
    public UserNameNotException(){
        super("userName이 일치하지 않습니다.");
    }
}
