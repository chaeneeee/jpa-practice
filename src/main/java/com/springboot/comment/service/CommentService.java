package com.springboot.comment.service;


import com.springboot.comment.entity.Comment;
import com.springboot.comment.mapper.CommentMapper;
import com.springboot.comment.repository.CommentRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.service.MemberService;
import com.springboot.poster.entity.Poster;
import com.springboot.poster.service.PosterService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static com.springboot.poster.entity.Poster.PostStatus.QUESTION_ANSWERED;

@Transactional
@Service
@Getter
public class CommentService {
    private CommentRepository commentRepository; //jpa srud
    private PosterService posterService;

    public CommentService(CommentRepository commentRepository, PosterService posterService) {
        this.commentRepository = commentRepository;
        this.posterService = posterService;
    }

    public Comment createComment(Comment comment) {
        Poster findPoster = posterService.findVerifyPoster(comment.getPoster().getPosterId());
        findPoster.setPosterStatus(QUESTION_ANSWERED);
        //        if (findPoster.getPosterStatus().equals(QUESTION_ANSWERED) &&  comment.getPoster().getPosterId().equals(comment)) {
//            { comment.setS;
        return commentRepository.save(comment);

    }

    public Comment UpdateComment (Comment comment){
        Comment findComment = findVerifiedComment(comment.getCommentId());
        Optional.ofNullable(comment.getContent()).ifPresent(content ->findComment.setContent(content));
        findComment.setModifyDate(LocalDateTime.now());
        return commentRepository.save(findComment);
    }



    public Comment findComment (long commentId){
        return findVerifiedComment(commentId);
    }

   public Page<Comment> findComments (int page, int size) {
        return  commentRepository.findAll(PageRequest.of(page, size, Sort.by("commentId").descending()));

   }

public void  deleteComment (long commentId) {
        Comment deleteComment = findVerifiedComment(commentId);
          commentRepository.delete(deleteComment);
}

    @Transactional(readOnly = true)
    public Comment findVerifiedComment (long commentId) {
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_COMMENT));
        return findComment;
    }



}











