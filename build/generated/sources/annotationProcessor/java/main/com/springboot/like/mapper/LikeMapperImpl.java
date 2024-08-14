package com.springboot.like.mapper;

import com.springboot.like.dto.LikePostDto;
import com.springboot.like.entity.Like;
import com.springboot.member.entity.Member;
import com.springboot.poster.entity.Poster;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T19:49:19+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.23 (Azul Systems, Inc.)"
)
@Component
public class LikeMapperImpl implements LikeMapper {

    @Override
    public Like likePostDtoToLike(LikePostDto likePostDto) {
        if ( likePostDto == null ) {
            return null;
        }

        Like like = new Like();

        like.setPoster( likePostDtoToPoster( likePostDto ) );
        like.setMember( likePostDtoToMember( likePostDto ) );
        like.setLikeId( likePostDto.getLikeId() );

        return like;
    }

    protected Poster likePostDtoToPoster(LikePostDto likePostDto) {
        if ( likePostDto == null ) {
            return null;
        }

        Poster poster = new Poster();

        poster.setPosterId( likePostDto.getPosterId() );

        return poster;
    }

    protected Member likePostDtoToMember(LikePostDto likePostDto) {
        if ( likePostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( likePostDto.getMemberId() );

        return member;
    }
}
