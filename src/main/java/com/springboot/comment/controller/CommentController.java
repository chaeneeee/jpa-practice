package com.springboot.comment.controller;


import com.springboot.comment.dto.CommentPatchDto;
import com.springboot.comment.dto.CommentPostDto;
import com.springboot.comment.entity.Comment;
import com.springboot.comment.mapper.CommentMapper;
import com.springboot.comment.service.CommentService;
import com.springboot.response.MultiResponseDto;
import com.springboot.response.SingleResponseDto;
import com.springboot.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v11/comments")
@Valid
@Slf4j
public class CommentController {
    private static final String COMMENT_DEFAULT_URI = "/v11/comments" ;
    private final CommentService commentService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }




    @PostMapping
    public ResponseEntity postComment (@Valid @RequestBody CommentPostDto commentPostDto) {
        Comment findComment = commentService.createComment(mapper.commentPostDtoToComment(commentPostDto));
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URI, findComment.getCommentId());
        return ResponseEntity.created(location).build();

    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment (@PathVariable("comment-id") @Positive long commentId,
                                        @Valid @RequestBody CommentPatchDto commentPatchDto){
       Comment comment = commentService.UpdateComment(mapper.commentPatchDtoToComment(commentPatchDto));
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment)), HttpStatus.OK
        );
    }

    @GetMapping("/{comment-id}")
    public ResponseEntity getComment (@PathVariable("comment-id") @Positive long commentId) {
        Comment comment = commentService.findComment(commentId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment)),HttpStatus.OK
        );
        }



      @GetMapping     //postman 에서 RequestParam 으로 size 와 page 수 찾을 수 있어야한다.
    public ResponseEntity getComments (@Positive @RequestParam int page ,
                                       @Positive @RequestParam int size) {
            Page<Comment> getComments = this.commentService.findComments(page-1 , size) ;
            List<Comment> comments = getComments.getContent();
            return  new ResponseEntity<>(
                    new MultiResponseDto<>(this.mapper.commentsToCommentResponse(comments), getComments),HttpStatus.OK
            );

        }



}
