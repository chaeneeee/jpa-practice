package com.springboot.poster.dto;

import com.springboot.comment.dto.CommentResponseDto;
import com.springboot.comment.entity.Comment;
import com.springboot.poster.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PosterResponseDto {
    private long memberId;
    private String  title;
    private String content;
    private int likeCount;
    private int view;
    private Poster.QuestionStatus questionStatus;
    private LocalDateTime createTime;
    private LocalDateTime modifyDate;
    private Poster.PostStatus posterStatus;
    private List<CommentResponseDto> comments;

}
