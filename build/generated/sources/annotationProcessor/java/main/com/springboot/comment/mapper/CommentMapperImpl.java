package com.springboot.comment.mapper;

import com.springboot.comment.dto.CommentPatchDto;
import com.springboot.comment.dto.CommentPostDto;
import com.springboot.comment.dto.CommentResponseDto;
import com.springboot.comment.entity.Comment;
import com.springboot.member.entity.Member;
import com.springboot.poster.entity.Poster;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T19:49:19+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.23 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(CommentPostDto commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setPoster( commentPostDtoToPoster( commentPostDto ) );
        comment.setMember( commentPostDtoToMember( commentPostDto ) );
        comment.setContent( commentPostDto.getContent() );

        return comment;
    }

    @Override
    public CommentResponseDto commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto();

        Long posterId = commentPosterPosterId( comment );
        if ( posterId != null ) {
            commentResponseDto.setPosterId( posterId );
        }
        Long memberId = commentMemberMemberId( comment );
        if ( memberId != null ) {
            commentResponseDto.setMemberId( memberId );
        }
        commentResponseDto.setContent( comment.getContent() );
        commentResponseDto.setCreateTime( comment.getCreateTime() );
        commentResponseDto.setModifyDate( comment.getModifyDate() );
        commentResponseDto.setCommentStatus( comment.getCommentStatus() );

        return commentResponseDto;
    }

    @Override
    public List<CommentResponseDto> commentsToCommentResponse(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponseDto> list = new ArrayList<CommentResponseDto>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentResponseDto( comment ) );
        }

        return list;
    }

    @Override
    public Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto) {
        if ( commentPatchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        return comment;
    }

    protected Poster commentPostDtoToPoster(CommentPostDto commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Poster poster = new Poster();

        poster.setPosterId( commentPostDto.getPosterId() );

        return poster;
    }

    protected Member commentPostDtoToMember(CommentPostDto commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( commentPostDto.getMemberId() );

        return member;
    }

    private Long commentPosterPosterId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Poster poster = comment.getPoster();
        if ( poster == null ) {
            return null;
        }
        Long posterId = poster.getPosterId();
        if ( posterId == null ) {
            return null;
        }
        return posterId;
    }

    private Long commentMemberMemberId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Member member = comment.getMember();
        if ( member == null ) {
            return null;
        }
        Long memberId = member.getMemberId();
        if ( memberId == null ) {
            return null;
        }
        return memberId;
    }
}
