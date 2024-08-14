package com.springboot.poster.service;

import com.springboot.comment.entity.Comment;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import com.springboot.member.service.MemberService;
import com.springboot.poster.entity.Poster;
import com.springboot.poster.mapper.PosterMapper;
import com.springboot.poster.repository.PostRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.springboot.poster.entity.Poster.PostStatus.*;

//서비스에서는 데이터와 상호작용해야하니까 repository 를 연결
@Transactional
@Service
public class PosterService {
private final PostRepository postRepository;
private final MemberService memberService;

    public PosterService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

public Poster createPoster(Poster poster) {

     memberService.findVerifiedMember(poster.getMember().getMemberId());
        return postRepository.save(poster);

}

public Poster updatePoster(Poster poster ){
Poster findPoster =findVerifyPoster(poster.getPosterId());
Optional.ofNullable(poster.getTitle()).ifPresent(title -> findPoster.setTitle(title));
    Optional.ofNullable(poster.getContent()).ifPresent(content -> findPoster.setContent(content));
    Optional.ofNullable(poster.getLikeCount()).ifPresent(likeCount -> findPoster.setLikeCount(likeCount));
    Optional.ofNullable(poster.getView()).ifPresent(view -> findPoster.setView(view));
//    Optional.ofNullable(poster.getQuestionStatus()).ifPresent(questionStatus-> findPoster.setQuestionStatus(questionStatus));
    Optional.ofNullable(poster.getPosterStatus()).ifPresent(postStatus -> findPoster.setPosterStatus(postStatus));
    findPoster.setModifyDate(LocalDateTime.now());
if (poster.getQuestionStatus() != null && poster.getQuestionStatus().equals(Poster.QuestionStatus.SECRET)){
    Optional.ofNullable(poster.getQuestionStatus()).ifPresent(questionStatus-> findPoster.setQuestionStatus(Poster.QuestionStatus.SECRET));
    findPoster.getComments().forEach(comment -> comment.setCommentStatus(Comment.CommentStatus.SECRET));

    }

    return postRepository.save(findPoster);


}

public Poster findPoster(long posterId ) {
        return findVerifyPoster(posterId);
//comment 랑 get set 연결해서 더 할 수 있는 거 코드 확인하기

}


    public Page<Poster> findPosters(int page, int size) {

        return postRepository.findAll(PageRequest.of(page,size, Sort.by("createTime").descending()));
    }

    public Page<Poster> sortByPoster (int page , int size , Sort sort ) {
    Sort SortPoster = Sort.by(Sort.Direction.DESC, "likeCount").descending();
        return postRepository.findAll(PageRequest.of(page,size,sort));
    }
    public Page<Poster> sortByPosterAsc (int page , int size , Sort sort ) {
        Sort SortPoster = Sort.by(Sort.Direction.ASC, "likeCount").ascending();
        return postRepository.findAll(PageRequest.of(page,size,sort));
    }


    public Page<Poster> sortByView(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "view");
        return postRepository.findAll(PageRequest.of(page, size, sort ));
    }

    public Page<Poster> sortByViewAsc (int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC , "view");
        return postRepository.findAll(PageRequest.of(page, size, sort ));
    }

    //클래스라는 큰 틀의 아이디를 가져올 일은 없다 poster 객체를 만들어서 가져와야한다.
    public void deletePoster(long posterId) {
        Poster deletePoster = findVerifyPoster(posterId);

        if (deletePoster.getPosterStatus().equals(QUESTION_REGISTERED) || deletePoster.getPosterStatus().equals(QUESTION_ANSWERED)) {
            deletePoster.setPosterStatus(QUESTION_DELETED);
            postRepository.save(deletePoster); // 상태 변경 후 저장
        } else {
            throw new BusinessLogicException(ExceptionCode.CAN_NOT_CHANGE_STATUS); // 상태 변경 불가 예외 처리
        } //지금 예외처리 던지는게 안된다.
    }




    public Poster findVerifyPoster (long posterId) {
        Optional <Poster> OptionalPoster = postRepository.findById(posterId);
        Poster findPoster = OptionalPoster.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findPoster;

    }





}





