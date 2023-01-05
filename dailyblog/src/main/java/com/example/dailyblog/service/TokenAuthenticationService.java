package com.example.dailyblog.service;


import com.example.dailyblog.dto.AuthenticationUser;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService implements AuthenticationService{
    private final JwtUtil jwtUtil;

    @Override
    public String resolveToken(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        return token;
    }

    @Override
    public void tokenVerification(String token ){

        //토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new TokenNotExistException();
        } else {
            //올바른 토큰이면 claims에 저장.
            jwtUtil.getUserInfoFromToken(token);
        }

    }


    @Override
    public AuthenticationUser getauthenticationUser(String token) {
        this.tokenVerification(token);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        //claims에서 role을 찾아서 스트링에 담기
        String role = claims.get("auth", String.class);
        String userName = claims.getSubject();

        return new AuthenticationUser(role,userName);


    }



}
