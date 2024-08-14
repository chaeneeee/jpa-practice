package com.springboot.like.service;


import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.like.dto.LikePostDto;
import com.springboot.like.entity.Like;
import com.springboot.like.mapper.LikeMapper;
import com.springboot.like.repository.LikeRepository;
import com.springboot.member.dto.MemberResponseDto;
import com.springboot.member.entity.Member;
import com.springboot.member.service.MemberService;
import com.springboot.poster.dto.PosterResponseDto;
import com.springboot.poster.entity.Poster;
import com.springboot.poster.repository.PostRepository;
import com.springboot.poster.service.PosterService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PosterService posterService;
    private final MemberService memberService;
    private final PostRepository postRepository;
    private final LikeMapper mapper;



    public LikeService(LikeRepository likeRepository, PosterService posterService, MemberService memberService, PostRepository postRepository, LikeMapper mapper) {
        this.likeRepository = likeRepository;
        this.posterService = posterService;
        this.memberService = memberService;
        this.postRepository = postRepository;
        this.mapper = mapper;

    }


    public void clickLike(LikePostDto likePostDto  ) {
        Member findMember = memberService.findVerifiedMember(likePostDto.getMemberId());
//      Like like = ;
        Poster findPoster = posterService.findVerifyPoster(likePostDto.getPosterId());
        Optional<Like>  optionalLike = likeRepository.findAllByMemberAndPoster(findMember , findPoster);


//    Optional<Like> likeOptional = likeRepository.findAllByMemberAndPoster(findMember , findPoster);
        if (optionalLike.isPresent()) {
            //likes 가 존재한다면
            Like likes = optionalLike.get(); //optionalLike 에 객체가 존재한다면 그 객체 반환
            likeRepository.deleteById(likes.getLikeId());
            findPoster.setLikeCount(findPoster.getLikeCount() -1);

            //get 은 Optional의 타입  Like 객체를 가져와야 repository.delete() 메서드에 실제 삭제할 객체를 전달
        }
        else {
            Like like = mapper.likePostDtoToLike(likePostDto);
            likeRepository.save(like);
            findPoster.setLikeCount(findPoster.getLikeCount() +1 ) ;

        }
        postRepository.save(findPoster); //if문에서도 else 문에서도 저장해야하니까

        //get으로 like 있는 지 값 가져오는 것
//좋아요를 찾아서 findposter 에 존재한다면 좋아요 객체를 찾고 밑에 메서드로
//        이 delete 에 넣는다
    }

//    public Like findVerifyidLike(long likeId) {
//        Optional <Like> optionalLike = likeRepository.findAllByMemberAndPoster()
//        Like findLike = optionalLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CAN_NOT_FOUND_LIKE));
//        return findLike;

    }



