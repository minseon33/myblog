package com.example.dailyblog.entity;

import com.example.dailyblog.dto.PostRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String userName;

    @OneToMany
    List<Comment> commentList = new ArrayList<>();





    public Post(PostRequestDto requestDto,String userName){
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.userName = userName;
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
        //여기서 루프돌려서 다 들어갈수 있게 해야된다.
    }


    public void update(PostRequestDto requestDto) {
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
    }





}
