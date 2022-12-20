package com.example.dailyblog.service;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.exception.PostNotExistException;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.exception.UserNameNotException;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.PostsRepository;
import io.jsonwebtoken.Claims;
import com.example.dailyblog.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyblogService {
    private final PostsRepository postsRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public Post creatPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto , user.getUsername());
        //게시물 작성하기
        postsRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postsRepository.findAllByOrderByModifiedAtDesc();
    }

//    하나의 선택글만 보기
    @Transactional(readOnly = true)
    public PostResponseDto showOnePost(Long id) {
        Post post = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, String token) {
        Claims claims;
        //토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new TokenNotExistException();
        }

        claims = jwtUtil.getUserInfoFromToken(token);
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);

        if (!claims.getSubject().equals(post.getUserName())) {
            throw new UserNameNotException();
        }
        post.update(requestDto);
        postsRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;
        if (!jwtUtil.validateToken(token)) {
            throw new TokenNotExistException();
        }
        claims = jwtUtil.getUserInfoFromToken(token);
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        if (!claims.getSubject().equals(post.getUserName())) {
            throw new UserNameNotException();
        }
        postsRepository.delete(post);
    }
}
