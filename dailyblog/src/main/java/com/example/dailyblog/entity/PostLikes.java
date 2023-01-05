package com.example.dailyblog.entity;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
public class PostLikes extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long likesId;

    @Column(nullable = false)
    private final Long likesCount;

    @Column(nullable = false)
    private final String userName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Likes_postNum", nullable = false)
    private final Long postNum;

}
