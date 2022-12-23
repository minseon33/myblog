package com.example.dailyblog.service;


import com.example.dailyblog.entity.User;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {
    private final JwtUtil jwtUtil;

    @Transactional
    public void tokenVerification(String token ){
        Claims claims;

        //토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new TokenNotExistException();
        } else {
            //올바른 토큰이면 claims에 저장.
            claims = jwtUtil.getUserInfoFromToken(token);
        }
    }

    @Transactional
    public String takeRole(String token ){

        Claims claims = jwtUtil.getUserInfoFromToken(token);
        //claims에서 role을 찾아서 스트링에 담기
        String role = claims.get("auth", String.class);

        return role;
    }

    @Transactional
    public String takeUserName(String token){
        Claims claims = jwtUtil.getUserInfoFromToken(token);

        //claims에서 userName 찾아서 username에 저장하기

        String userName = claims.getSubject();

        return userName;

    }


}
