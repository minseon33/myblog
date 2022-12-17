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

        // Q.왜 2번부터 등록이 되는거지...?

        //토큰 검증해서 true면 게시물 작성하기
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postsRepository.findAllByOrderByModifiedAtDesc();
    }

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
        if(!jwtUtil.validateToken(token))throw new TokenNotExistException();//토큰 있는지 없는지 확인. 만약 없으면 에러처리, Q. 에러처리 IllegalArgumentException 이게 맞나..?
        claims = jwtUtil.getUserInfoFromToken(token); //토큰에서 사용자 정보 가지고 오기. // 왜 Claims 클래스에 담는거지.. 얘가 하는 역할이 뭔데...?
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new); // 수정하려는 글이 있는지 먼저 확인을 하고
        System.out.println("토큰 속 유저네임" + claims.getSubject());
        System.out.println("포스트 속 유저네임" + post.getUserName());
        if(!claims.getSubject().equals(post.getUserName()))throw new UserNameNotException(); //토큰의 유저아이디와 선택한 게시글의 유저아이디가 같지않으면 에러

        // 에러처리 이것밖에 몰라서 이거 쓰는데.. 에러처리 뭐가있는지 다시 알아보자.
        post.checkPassword(requestDto.getPassword()); //post한테 비밀번호 맞는지 확인하라고 시키고
        post.update(requestDto);
        postsRepository.save(post);
        return new PostResponseDto(post);
    }

    public void delete(Long id, PostDeleteDto postDeleteDto) {
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        post.checkPassword(postDeleteDto.getUserPassword());
        postsRepository.delete(post);
    }
}
