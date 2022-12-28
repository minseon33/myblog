package com.example.dailyblog.service;

import com.example.dailyblog.dto.CommentRequestDto;
import com.example.dailyblog.dto.CommentResponseDto;
import com.example.dailyblog.entity.Comment;
import com.example.dailyblog.entity.Post;
import com.example.dailyblog.jwt.JwtUtil;
import com.example.dailyblog.repository.CommentsRepository;
import com.example.dailyblog.repository.PostsRepository;
import com.example.dailyblog.exception.PostNotExistException;
import com.example.dailyblog.exception.CommentNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final JwtUtil jwtUtil;
    private final PostsRepository postsRepository;


    //코멘트 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String userName, Long postNum) {
        //Dto는 한칸만 넘어가야 한다.!!! 아하.>!

        //댓글을 작성할 공간(게시판)을 찾아가는 것이다.
        Post post = postsRepository.findById(postNum).orElseThrow(PostNotExistException::new);

        // 게시판 찾기 완료
        String content = commentRequestDto.getContents();

        Comment comment = new Comment(userName,content, post);
        post.addComment(comment);

//        //게시물 작성하기
//        post.addComment(comment);
        commentsRepository.save(comment);
        postsRepository.save(post);

        return new CommentResponseDto(comment);
    }



    //코멘트 업데이트
    @Transactional
    public CommentResponseDto userUpdateComment(CommentRequestDto commentRequestDto,String userName, Long postNum,Long commentNum){
        // 댓글이 달려있는 포스트를 찾아줌.
        Post post = postsRepository.findById(postNum).orElseThrow(PostNotExistException::new);
        // post안에 있는 댓글들 중에 내가 쓴 댓글이 있는지 찾아야 함.

        //코멘트 위치 확인
        Comment comment = commentsRepository.findById(commentNum).orElseThrow(CommentNotExistException::new);

        String writerName = userName.toString();
        System.out.println("userName : " + writerName );

        //댓글작성자 아이디 비교 (왜 안먹지)
        comment.checkedCommentWriterName(writerName);


        comment.commentUpdate(commentRequestDto);
        commentsRepository.save(comment);
        postsRepository.save(post);

        return new CommentResponseDto(comment);

    }
    @Transactional
    public void adminCommentDelet(Long postNum,Long commentNum){
        // 댓글이 달려있는 포스트를 찾아줌.
        Post post = postsRepository.findById(postNum).orElseThrow(PostNotExistException::new);
        Comment comment = commentsRepository.findById(commentNum).orElseThrow(CommentNotExistException::new);
        commentsRepository.delete(comment);

    }

    @Transactional
    public void userCommentDelet(Long postNum,Long commentNum,String userName){
        //댓글이 달려있는 포스트를 찾아줌.
        Post post = postsRepository.findById(postNum).orElseThrow(PostNotExistException::new);

        //코멘트번호로 지우고자 하는 코멘트를 찾아줌.
        Comment comment = commentsRepository.findById(commentNum).orElseThrow(CommentNotExistException::new);

        //코멘트가 userName 과 같은지 확인해준 뒤 삭제. 헷갈리니까 이름 writerName 으로 바꿔주고..
        String writerName = userName.toString();

        //코멘트의 writerName이 삭제하려는 댓글의 writerName과 같은지 비교해주고
        comment.checkedCommentWriterName(writerName);
        commentsRepository.delete(comment);


    }
}
