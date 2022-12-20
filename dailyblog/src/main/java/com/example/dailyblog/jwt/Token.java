package com.example.dailyblog.jwt;


import com.example.dailyblog.entity.User;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Getter
@RequiredArgsConstructor
public class Token {
    private final User user;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public User checkUserRoleToken(HttpServletRequest httpServletRequest){
        Claims claims;

        //httpServletRequest 에서 토큰값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new TokenNotExistException();
        } else {
            //올바른 토큰이면 claims에 저장.
            claims = jwtUtil.getUserInfoFromToken(token);
        }

        //claims에서 username을 찾아서 레파지토리에 담기. (이부분 살짝 이해 안됨)
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        return user;
    }
}
