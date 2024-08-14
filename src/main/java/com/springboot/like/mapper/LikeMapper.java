package com.springboot.like.mapper;

import com.springboot.like.dto.LikePostDto;
import com.springboot.like.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//mapper 는 받아서 전달하는 거니까 interface 로 구현
@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(source = "posterId" , target = "poster.posterId")
    @Mapping(source = "memberId", target = "member.memberId")
    Like likePostDtoToLike (LikePostDto likePostDto);


}
