package com.example.dailyblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;


    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Long postNum;


    public PostLikes(Long postNum,String userName){
        this.postNum = postNum;
        this.userName = userName;
    }

}
