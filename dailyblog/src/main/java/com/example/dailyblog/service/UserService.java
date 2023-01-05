package com.example.dailyblog.service;

import com.example.dailyblog.dto.LoginRequestDto;
import com.example.dailyblog.dto.LoginResponseDto;
import com.example.dailyblog.dto.SignupRequestDto;
import com.example.dailyblog.dto.SignupResponseDto;
import com.example.dailyblog.entity.User;
import com.example.dailyblog.entity.UserRoleEnum;
import com.example.dailyblog.exception.UserIdNotExistException;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor //얘가 final 달린 애 생성자를 기본으로 만들어주니까...
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto,UserRoleEnum role) {

        // 회원 중복 확인
        Optional<User> findUserId = userRepository.findByUsername(signupRequestDto.getUserName());
        if(findUserId.isPresent()){
            throw new UserIdNotExistException();
        }
        User user = new User(signupRequestDto,role);
        userRepository.save(user);
        return new SignupResponseDto("회원가입 완료",200);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto) {
        String userName = loginRequestDto.getUserName();
        String userPassword = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        user.checkPassword(userPassword);
        String generatedToken = jwtUtil.createToken(user.getUsername(),user.getRole());

        return generatedToken;
    }

}
