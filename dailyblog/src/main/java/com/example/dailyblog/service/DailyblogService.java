package com.example.dailyblog.service;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.exception.PostNotExistException;
import com.example.dailyblog.exception.TokenNotExistException;
import com.example.dailyblog.exception.UserNameNotException;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.CommentsRepository;
import com.example.dailyblog.repository.PostsRepository;
import io.jsonwebtoken.Claims;
import com.example.dailyblog.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyblogService {
    private final PostsRepository postsRepository;
    private final JwtUtil jwtUtil;

    private final CommentsRepository commentsRepository;

//    private final User user;


    @Transactional
    public Post creatPost(PostRequestDto postRequestDto, String userName) {
        Post post = new Post(postRequestDto, userName);
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
    public PostResponseDto adminUpdate(Long id, PostRequestDto requestDto) {
        //레파지토리에서 post 꺼내오기.
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        //게시글 수정하기
        post.update(requestDto);
        postsRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto userUpdate(Long id, PostRequestDto requestDto, String userName) {
        //레파지토리에서 post 꺼내오기.
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        //아이디 체크하기
        if (!post.getUserName().equals(userName)) {
            throw new UserNameNotException();
        }
        //게시글 수정하기
        post.update(requestDto);
        postsRepository.save(post);

        return new PostResponseDto(post);
    }


    @Transactional
    public void adminDelete(Long id) {
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        postsRepository.delete(post);
    }


    @Transactional(rollbackFor = Exception.class)
    public void userDelete(Long id,String userName) {
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        if (!post.getUserName().equals(userName)) {
            throw new UserNameNotException();
        }
        postsRepository.delete(post);
    }
}
