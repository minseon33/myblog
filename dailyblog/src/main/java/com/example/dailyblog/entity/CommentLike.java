package com.example.dailyblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;


    @Column(nullable = false)
    private String userName;

    @Column
    private Long commentNum;


    public CommentLike(Long commentNum, String userName){
        this.commentNum = commentNum;
        this.userName = userName;
    }

}
