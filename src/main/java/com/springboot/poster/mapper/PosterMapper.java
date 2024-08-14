package com.springboot.poster.mapper;


import com.springboot.comment.dto.CommentResponseDto;
import com.springboot.comment.entity.Comment;
import com.springboot.comment.mapper.CommentMapper;
import com.springboot.member.entity.Member;
import com.springboot.poster.dto.PosterPatchDto;
import com.springboot.poster.dto.PosterPostDto;
import com.springboot.poster.dto.PosterResponseDto;
import com.springboot.poster.entity.Poster;
import com.springboot.poster.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE , uses = {CommentMapper.class})
public interface PosterMapper {



    @Mapping(source = "memberId", target = "member.memberId")
    Poster posterPostDtoToPoster(PosterPostDto posterPostDto);
 //디티오인 PostDto를 Poster엔티티로 보낸다

 //PostDto를 엔티티로 보낸다


    @Mapping(source = "commentId", target = "comment.commentId")
    default List<CommentResponseDto> commentsToCommentResponseDto(List<Comment> comments) {
            return comments.stream()
                    .map(comment -> {
                        CommentResponseDto dto = new CommentResponseDto(); //매개변수 없는데 에러뜬거면 @No 에너테이션 붙여아한다
                        dto.setContent(comment.getContent());
                        dto.setCreateTime(comment.getCreateTime());
                        dto.setModifyDate(comment.getModifyDate());
                        dto.setMemberId(comment.getMember().getMemberId());
                        dto.setPosterId(comment.getPoster().getPosterId());


                        return dto;
                    })
                    .collect(Collectors.toList());
        }




//    @Mapping(source = "memberId", target = "member.memberId")
    @Mapping(source = "commentId" , target = "comment.commentId" )
    Poster posterPatchDtoToPoster (PosterPatchDto posterPatchDto);


    List<PosterResponseDto>postersToPosterResponses(List<Poster> posters);
    //엔티티를 디티오로 보낸다
    //멤버아이디로 멤버를 만들어야한다

    //default 같은 패키지
//    @Mapping(source = "memberId", target = "member.memberId")
//    @Mapping(source = "commentId" , target = "comment.commentId" )
//    default PosterResponseDto postToPosterResponseDto (Poster poster) {
//        PosterResponseDto posterResponseDto = new PosterResponseDto();
////response 는 get 요청에서 보내줄 애들 설정해주는 것
//        posterResponseDto.setMemberId(poster.getMember().getMemberId());
//        posterResponseDto.setTitle(poster.getTitle());
//        posterResponseDto.setContent(poster.getContent());
//        posterResponseDto.setLikeCount(poster.getLikeCount());
//        posterResponseDto.setView(poster.getView());
//        posterResponseDto.setCreateTime(poster.getCreateTime());
//        posterResponseDto.setModifyDate(poster.getModifyDate());
//        posterResponseDto.setPostStatus(poster.getPosterStatus());
//        posterResponseDto.setComments(commentsToCommentResponseDto(poster.getComments()));
//        posterResponseDto.setQuestionStatus(poster.getQuestionStatus());
//
//
//        return posterResponseDto;
//    }
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(target = "comments" , qualifiedByName = "commentsToCommentResponses")
   PosterResponseDto postToPosterResponseDto (Poster poster) ;

//response 는 get 요청에서 보내줄 애들 설정해주는 것

//
//    @Mapping(source = "commentId", target = "comment.commentId")
//    default PosterResponseDto postToPosterResponseDto(Poster poster, List<Comment> comments) {
//        PosterResponseDto posterResponseDto = new PosterResponseDto();
//        posterResponseDto.setMemberId(poster.getMember().getMemberId());
//        posterResponseDto.setTitle(poster.getTitle());
//        posterResponseDto.setContent(poster.getContent());
//        posterResponseDto.setLikeCount(poster.getLikeCount());
//        posterResponseDto.setView(poster.getView());
//        posterResponseDto.setCreateTime(poster.getCreateTime());+
//        posterResponseDto.setModifyDate(poster.getModifyDate());
//        posterResponseDto.setPostStatus(poster.getPosterStatus());
//        // Convert comments to CommentResponseDto list
//        List<CommentResponseDto> commentResponseDto = commentsToCommentResponseDto(comments);
//        posterResponseDto.setComments(commentResponseDto);
//
//        return posterResponseDto;
//    }


}
