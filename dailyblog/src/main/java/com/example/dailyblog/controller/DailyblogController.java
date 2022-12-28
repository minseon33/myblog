package com.example.dailyblog.controller;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.entity.User;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.service.TokenAuthenticationService;
import com.example.dailyblog.repository.UserRepository;
import com.example.dailyblog.service.DailyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyblogController {
    private final DailyblogService dailyblogService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenAuthenticationService tokenAuthenticationService;

    //홈화면
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //게시물 등록
    @PostMapping("/posts/dailypost")
    public PostResponseDto creatPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {
        //httpServletRequest 에서 토큰값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        tokenAuthenticationService.tokenVerification(token);

        //토큰에서 role값 꺼내기
        String role = tokenAuthenticationService.getauthenticationUser(token).getRole();

        //토큰에서 userName 값 꺼내기
        String userName = tokenAuthenticationService.getauthenticationUser(token).getUserName();

        //user 확인하기
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //권한이랑 같이 서비스로 값 넘겨줌
        return dailyblogService.creatPost(postRequestDto, userName);
    }

    //전체 게시물 보기
    @GetMapping("/posts/dailypost")
    public List<PostResponseDto> getPosts() {
        return dailyblogService.getPosts();
    }

    //게시물 수정
    @PutMapping("/posts/dailypost/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {


        //httpServletRequest 에서 토큰값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        tokenAuthenticationService.tokenVerification(token);

        //토큰에서 role값 꺼내기
        String role = tokenAuthenticationService.getauthenticationUser(token).getRole();

        //토큰에서 userName 값 꺼내기
        String userName = tokenAuthenticationService.getauthenticationUser(token).getUserName();

        //user 확인하기
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 권한에 따라 나누기
        if(role.equals("ADMIN")){
            return dailyblogService.adminUpdate(id, postRequestDto);
        }else {
            return dailyblogService.userUpdate(id,postRequestDto,userName);
        }

    }

    //게시물 삭제
    @DeleteMapping("/posts/dailypost/{id}")
    public void deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        //httpServletRequest 에서 토큰값 꺼내기
        String token = jwtUtil.resolveToken(httpServletRequest);

        //토큰 검증
        tokenAuthenticationService.tokenVerification(token);

        //토큰에서 role값 꺼내기
        String role = tokenAuthenticationService.getauthenticationUser(token).getRole();

        //토큰에서 userName 값 꺼내기
        String userName = tokenAuthenticationService.getauthenticationUser(token).getUserName();

        //user 확인하기
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 권한에 따라 나누기
        if(role.equals("ADMIN")){
            dailyblogService.adminDelete(id);
        }else {
            dailyblogService.userDelete(id, userName);
        }

    }

    //선택 게시물 보기
    @GetMapping("/posts/dailypost/{id}")
    public PostResponseDto showPosts(@PathVariable Long id) {
        return dailyblogService.showOnePost(id);
    }


//    @GetMapping("/posts/dailypost/{id}/comment/{commentid}")
//    public PostResponseDto showPosts(@PathVariable Long id ,@PathVariable Long commentid) {
//        return dailyblogService.showOnePost(id);
//    }


}
