package com.example.dailyblog.service;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
