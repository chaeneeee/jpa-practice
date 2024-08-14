package com.springboot.comment.mapper;

import com.springboot.comment.dto.CommentPatchDto;
import com.springboot.comment.dto.CommentPostDto;
import com.springboot.comment.dto.CommentResponseDto;
import com.springboot.comment.entity.Comment;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "posterId" , target ="poster.posterId")
    @Mapping(source = "memberId" , target ="member.memberId")
    Comment commentPostDtoToComment (CommentPostDto commentPostDto);
//얘는 dto 를 받아서 엔티티로 보내고 밑은 엔티티를 받아서 디티오로 보내니 소스 타겟 바꾸는 것

    @Mapping(source = "poster.posterId" , target ="posterId")
    @Mapping(source = "member.memberId" , target = "memberId" )
    CommentResponseDto commentToCommentResponseDto (Comment comment);


    @Named("commentsToCommentResponses")
//    @Mapping(source = "posterId" , target ="poster.posterId")
    List<CommentResponseDto> commentsToCommentResponse (List<Comment> comments);


    Comment commentPatchDtoToComment (CommentPatchDto commentPatchDto);

}

