package com.example.dailyblog.entity;


import com.example.dailyblog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long commentId;

    @Column(nullable = false)
    private String commentContents;

    @Column(nullable = false)
    private String commentWriterName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_postNum", nullable = false)
    private Post post;

    public Comment(String userName,CommentRequestDto commentRequestDto,Post post){
        this.commentWriterName = userName;
        this.commentContents = commentRequestDto.getContents();
        this.post = post;
        //정적 팩토리 메서드..?
    }


}
