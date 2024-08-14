package com.springboot.like.controller;

import com.springboot.like.dto.LikePostDto;
import com.springboot.like.entity.Like;
import com.springboot.like.mapper.LikeMapper;
import com.springboot.like.repository.LikeRepository;
import com.springboot.like.service.LikeService;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v11/likes")
@Validated
public class LikeController {
    private final LikeService likeService;
    private final LikeMapper mapper;


    public LikeController(LikeService likeService, LikeMapper mapper) {
        this.likeService = likeService;
        this.mapper = mapper;
    }

    @PostMapping
    public void  postLike(@Valid @RequestBody LikePostDto likePostDto) {
       Like findLike = mapper.likePostDtoToLike(likePostDto);
       likeService.clickLike(likePostDto);

        //mapper 로 dto 로 받은 요청을 엔티티로 바꾸고
        //서비스에서 해결해야한다.

    }
    }

