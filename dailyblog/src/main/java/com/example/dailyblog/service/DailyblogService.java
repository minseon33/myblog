package com.example.dailyblog.service;

import com.example.dailyblog.dto.PostDeleteDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.exception.PostNotExistException;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.exception.UserNameNotException;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.PostsRepository;
import io.jsonwebtoken.Claims;
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
    public Post createBulletin(PostRequestDto postRequestDto , HttpServletRequest httpServletRequest) {
        //토큰 있는지 없는지 확인,
        //토큰이 있으면 게시물 등록 가능
        Post post = new Post(postRequestDto);
        String token = jwtUtil.resolveToken(httpServletRequest); // 토큰 가져오기
        if(!jwtUtil.validateToken(token)) throw new TokenNotExistException();
        postsRepository.save(post);

        //토큰 검증해서 true면 게시물 작성하기
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postsRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public PostResponseDto showOnePost(Long id){
        Post post = postsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto,HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest); // 토큰 가져오기
        Claims claims;
        if(!jwtUtil.validateToken(token)){
            throw new TokenNotExistException();
        }

        claims = jwtUtil.getUserInfoFromToken(token);
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);

        if(!claims.getSubject().equals(post.getUserName())){
            throw new UserNameNotException();
        }
        post.checkPassword(requestDto.getPassword());
        post.update(requestDto);
        postsRepository.save(post);
        return new PostResponseDto(post);
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, PostRequestDto requestDto , HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;
        if(!jwtUtil.validateToken(token)){
            throw new TokenNotExistException();
        }
        claims = jwtUtil.getUserInfoFromToken(token);
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        if(!claims.getSubject().equals(post.getUserName())){
            throw new UserNameNotException();
        }
        post.checkPassword(requestDto.getPassword());
        postsRepository.delete(post);
    }
}
