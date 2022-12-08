package com.example.dailyblog.controller;

import com.example.dailyblog.dto.PostDeleteDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Posts;
import com.example.dailyblog.service.DailyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyblogController {
    private final DailyblogService dailyblogService;

    @GetMapping("/")
    public ModelAndView home() {return new ModelAndView("index");
    }

    @PostMapping("/posts/dailypost")
    public Posts creatPost(@RequestBody PostRequestDto postRequestDto){
        return dailyblogService.createBulletin(postRequestDto);
    }

    @GetMapping("/posts/dailypost")
    public List<Posts> getPosts(){
        return dailyblogService.getPosts();
    }

    @PutMapping("/posts/dailypost/{id}")
    public PostResponseDto updatePost(@PathVariable Long id , @RequestBody PostRequestDto requestDto){
        return dailyblogService.update(id, requestDto);
    }

    @DeleteMapping("/posts/dailypost/{id}")
    public void deletePost(@PathVariable Long id ,@RequestBody PostDeleteDto postDeleteDto){
        dailyblogService.delete(id,postDeleteDto);
    }

    @GetMapping("/posts/dailypost/{id}")
    public PostResponseDto showPosts(@PathVariable Long id){
        return dailyblogService.showOnePost(id);
    }



}
