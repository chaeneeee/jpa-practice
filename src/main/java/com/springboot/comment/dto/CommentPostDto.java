package com.springboot.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CommentPostDto {
    @NotBlank(message = "내용은 공백이 아니어야 합니다.")
    private String content;

    private long memberId;

    private long posterId;

}
