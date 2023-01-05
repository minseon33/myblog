package com.example.dailyblog.entity;

import com.example.dailyblog.dto.PostRequestDto;

import com.example.dailyblog.exception.UserNameNotException;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNum;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = false)
    private String userName;


    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<Comment> commentList = new ArrayList<>();



    public Post(PostRequestDto requestDto,String userName){
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.userName = userName;
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
        //주먹질이다.. 한개의 주먹으로 여러번 때리는것이다...~~ 반복은 컴퓨터가 알아서 할것이다.
        //댓글을 넣는게 한번에 리스트로 다 넣는게 아니다.. 하나만 만들어서 하나만 넣는다..

//        리스트를 어떻게 다 붙이지..?
//        this.commentList.add(comment);
//        여기서 루프돌려서 다 들어갈수 있게 해야되나...?

    }


    public void postUpdate(PostRequestDto requestDto) {
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
    }


//    public void ckeckedId(String userName){
//        if (!this.UserName().equals(userName)) {
//            throw new UserNameNotException();
//        }
//    }







}
