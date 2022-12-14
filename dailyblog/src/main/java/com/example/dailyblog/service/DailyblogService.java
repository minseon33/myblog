package com.example.dailyblog.service;

import com.example.dailyblog.dto.PostDeleteDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.exception.PostNotExistException;
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
    public Post createBulletin(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postsRepository.save(post);
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
    public PostResponseDto update(Long id, PostRequestDto requestDto) {
        //아이디 있는지 확인 해,
        //비밀번호 맞는지 확인 해.
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        post.checkPassword(requestDto.getClientPassword());
        post.update(requestDto);
        postsRepository.save(post);
        return new PostResponseDto(post);
    }

    public void delete(Long id, PostDeleteDto postDeleteDto) {
        Post post = postsRepository.findById(id).orElseThrow(PostNotExistException::new);
        post.checkPassword(postDeleteDto.getClientPassword());
        postsRepository.delete(post);
    }
}
