package com.example.dailyblog.controller;

import com.example.dailyblog.dto.PostRequestDto;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.service.DailyblogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class DailyblogController {
    private final DailyblogService dailyblogService;

    @GetMapping("/")
    public ModelAndView home() {return new ModelAndView("index");
    }

    @GetMapping("/bulletins/dailypost")
    public Post createBulletin(@RequestBody PostRequestDto postRequestDto){
        return dailyblogService.createBulletin(postRequestDto);
    }


}
