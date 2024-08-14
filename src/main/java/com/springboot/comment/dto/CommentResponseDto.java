package com.springboot.comment.dto;


import com.springboot.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime modifyDate;
    private long memberId;
    private long posterId;
    private Comment.CommentStatus commentStatus;

}
