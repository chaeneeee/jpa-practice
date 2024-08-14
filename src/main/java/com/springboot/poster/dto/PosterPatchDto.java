package com.springboot.poster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PosterPatchDto {
    private Long posterId;
    private String title;
    private String content;
    private  int likeCount;
    private  int view;
    private String questionStatus;
    private long commentId;

    public void setPosterId(long posterId){
        this.posterId = posterId;
    }


}
