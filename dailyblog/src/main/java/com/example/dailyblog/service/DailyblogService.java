package com.example.dailyblog.service;

import com.example.dailyblog.dto.PostDeleteDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Posts;
import com.example.dailyblog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyblogService {
    private final PostsRepository postsRepository;

    @Transactional
    public Posts createBulletin(PostRequestDto postRequestDto) {
        Posts posts = new Posts(postRequestDto);
        postsRepository.save(posts);
        return posts;
    }

    @Transactional(readOnly = true)
    public List<Posts> getPosts() {
        return postsRepository.findAllByOrderByModifiedAtDesc();
    }

    public PostResponseDto showOnePost(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(posts);
    }

    public PostResponseDto update(Long id, PostRequestDto requestDto) {
        //아이디 있는지 확인 해,
        //비밀번호 맞는지 확인 해.
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        posts.checkPassword(requestDto.getClientPassword());
        posts.update(requestDto);
        return new PostResponseDto(posts);
    }

    public void delete(Long id, PostDeleteDto postDeleteDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        posts.checkPassword(postDeleteDto.getClientPassword());
        postsRepository.delete(posts);
    }
}
