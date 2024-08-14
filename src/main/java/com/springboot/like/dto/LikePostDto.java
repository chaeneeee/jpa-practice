package com.springboot.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LikePostDto {

    private long likeId;

    private long memberId;

    private long posterId;
}
