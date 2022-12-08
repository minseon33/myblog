package com.example.dailyblog.controller;

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

    @PostMapping("/bulletins/dailypost")
    public Posts createBulletin(@RequestBody PostRequestDto postRequestDto){
        return dailyblogService.createBulletin(postRequestDto);
    }

    @GetMapping("/bulletins/dailypost")
    public List<Posts> getPosts(){
        return dailyblogService.getPosts();
    }

    public PostResponseDto updatePost(@PathVariable Long id , @RequestBody PostResponseDto responseDto){
        return dailyblogService.update(id, responseDto);
    }



}
