package com.example.dailyblog.entity;

import com.example.dailyblog.dto.PostRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postNum;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @NotNull
    private String clientName;

    @NotNull
    private String clientPassword;


    public Post(PostRequestDto requestDto){
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
    }


}
