package com.example.dailyblog.service;

import com.example.dailyblog.dto.CommentResponseDto;
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
    public List<PostResponseDto> getPosts() {
        //여기서 모든 데이터를 뽑아오는데 그대로 내려주면 안된다. DB에 담긴 날것의 값이니까 그대로 주면 안된다.
        // Dto 로 제한된 정보의 값만 넘겨주어야 한다.
        List<Post> postList = postsRepository.findAllByOrderByModifiedAtDesc();

        // 담아줄 빈 객체를 생성.
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }

        // 순환참조가 일어난 이유.. post값을 그대로 내려줘서 그렇다... == 엔티티를 그대로 내려줘서 그렇다...일단 암기.. 엔티티는 그대로 내려주면 안된다..
        //제이슨 이그노어는 순환참조를 그냥 억지로 끊어내고 엔티티를 그래도 내려주는것이다...! 아하..! 빌런짓이다~~ 이해했습니다.
        // 디티오는 객체를 가지고 있지 않다..


        return postResponseDtoList;

        //for문 돌려서 포스트 하나씩 있는 객체들을 객체들 하나하나를 PostResponseDto 로 바꿔준다.


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
        post.postUpdate(requestDto);
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
        post.postUpdate(requestDto);
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
