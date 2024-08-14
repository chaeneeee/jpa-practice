package com.springboot.poster.dto;

import com.springboot.poster.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class PosterPostDto {

    //자동생성이니까 Id는 x
    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;

    @NotBlank(message = "내용은 공백이 아니어야 합니다.")
    private String content;

    //멤버아이디 받기
    @Positive
    private Long memberId;



}

