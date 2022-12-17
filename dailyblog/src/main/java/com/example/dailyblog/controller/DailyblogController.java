package com.example.dailyblog.controller;

import com.example.dailyblog.dto.PostDeleteDto;
import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.dto.PostResponseDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.service.DailyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyblogController {
    private final DailyblogService dailyblogService;

    //홈화면
    @GetMapping("/")
    public ModelAndView home() {return new ModelAndView("index");
    }

    //게시물 등록
    @PostMapping("/posts/dailypost")
    public Post creatPost(@RequestBody PostRequestDto postRequestDto , HttpServletRequest httpServletRequest){
        return dailyblogService.createBulletin(postRequestDto,httpServletRequest);
    }

    //전체 게시물 보기
    @GetMapping("/posts/dailypost")
    public List<Post> getPosts(){return dailyblogService.getPosts();}

    //게시물 수정
    @PutMapping("/posts/dailypost/{id}")
    public PostResponseDto updatePost(@PathVariable Long id , @RequestBody PostRequestDto requestDto,HttpServletRequest httpServletRequest){
        return dailyblogService.update(id, requestDto,httpServletRequest);
    }

    //게시물 삭제
    @DeleteMapping("/posts/dailypost/{id}")
    public void deletePost(@PathVariable Long id ,@RequestBody PostDeleteDto postDeleteDto){
        dailyblogService.delete(id,postDeleteDto);
    }

    //선택 게시물 보기
    @GetMapping("/posts/dailypost/{id}")
    public PostResponseDto showPosts(@PathVariable Long id){
        return dailyblogService.showOnePost(id);
    }



}
