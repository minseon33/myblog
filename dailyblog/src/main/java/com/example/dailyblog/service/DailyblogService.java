package com.example.dailyblog.service;

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

    public PostResponseDto update(Long id, PostResponseDto responseDto) {
        
    }
}
