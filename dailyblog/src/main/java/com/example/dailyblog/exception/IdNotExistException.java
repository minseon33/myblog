package com.example.dailyblog.exception;

public class IdNotExistException extends IllegalArgumentException{
    public IdNotExistException(){
        super("아이디가 존재하지 않습니다.");
    }
}