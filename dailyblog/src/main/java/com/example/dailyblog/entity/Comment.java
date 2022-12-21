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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long commentId;

    @Column(nullable = false)
    private String commentContents;

    @Column(nullable = false)
    private String commentWriterName;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto commentRequestDto,Post post){
        this.commentWriterName = commentRequestDto.getCommentWriterName();
        this.commentContents = commentRequestDto.getContents();
        this.post = post;
    }


}
