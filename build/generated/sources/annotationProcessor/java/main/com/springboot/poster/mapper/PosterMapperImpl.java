package com.springboot.poster.mapper;

import com.springboot.comment.entity.Comment;
import com.springboot.comment.mapper.CommentMapper;
import com.springboot.member.entity.Member;
import com.springboot.poster.dto.PosterPatchDto;
import com.springboot.poster.dto.PosterPostDto;
import com.springboot.poster.dto.PosterResponseDto;
import com.springboot.poster.entity.Poster;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T19:49:19+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.23 (Azul Systems, Inc.)"
)
@Component
public class PosterMapperImpl implements PosterMapper {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Poster posterPostDtoToPoster(PosterPostDto posterPostDto) {
        if ( posterPostDto == null ) {
            return null;
        }

        Poster poster = new Poster();

        poster.setMember( posterPostDtoToMember( posterPostDto ) );
        poster.setTitle( posterPostDto.getTitle() );
        poster.setContent( posterPostDto.getContent() );

        return poster;
    }

    @Override
    public Poster posterPatchDtoToPoster(PosterPatchDto posterPatchDto) {
        if ( posterPatchDto == null ) {
            return null;
        }

        Poster poster = new Poster();

        poster.setComment( posterPatchDtoToComment( posterPatchDto ) );
        poster.setPosterId( posterPatchDto.getPosterId() );
        poster.setTitle( posterPatchDto.getTitle() );
        poster.setContent( posterPatchDto.getContent() );
        poster.setLikeCount( posterPatchDto.getLikeCount() );
        poster.setView( posterPatchDto.getView() );
        if ( posterPatchDto.getQuestionStatus() != null ) {
            poster.setQuestionStatus( Enum.valueOf( Poster.QuestionStatus.class, posterPatchDto.getQuestionStatus() ) );
        }

        return poster;
    }

    @Override
    public List<PosterResponseDto> postersToPosterResponses(List<Poster> posters) {
        if ( posters == null ) {
            return null;
        }

        List<PosterResponseDto> list = new ArrayList<PosterResponseDto>( posters.size() );
        for ( Poster poster : posters ) {
            list.add( postToPosterResponseDto( poster ) );
        }

        return list;
    }

    @Override
    public PosterResponseDto postToPosterResponseDto(Poster poster) {
        if ( poster == null ) {
            return null;
        }

        PosterResponseDto posterResponseDto = new PosterResponseDto();

        Long memberId = posterMemberMemberId( poster );
        if ( memberId != null ) {
            posterResponseDto.setMemberId( memberId );
        }
        posterResponseDto.setComments( commentMapper.commentsToCommentResponse( poster.getComments() ) );
        posterResponseDto.setTitle( poster.getTitle() );
        posterResponseDto.setContent( poster.getContent() );
        posterResponseDto.setLikeCount( poster.getLikeCount() );
        posterResponseDto.setView( poster.getView() );
        posterResponseDto.setQuestionStatus( poster.getQuestionStatus() );
        posterResponseDto.setCreateTime( poster.getCreateTime() );
        posterResponseDto.setModifyDate( poster.getModifyDate() );
        posterResponseDto.setPosterStatus( poster.getPosterStatus() );

        return posterResponseDto;
    }

    protected Member posterPostDtoToMember(PosterPostDto posterPostDto) {
        if ( posterPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( posterPostDto.getMemberId() );

        return member;
    }

    protected Comment posterPatchDtoToComment(PosterPatchDto posterPatchDto) {
        if ( posterPatchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( posterPatchDto.getCommentId() );

        return comment;
    }

    private Long posterMemberMemberId(Poster poster) {
        if ( poster == null ) {
            return null;
        }
        Member member = poster.getMember();
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
