package com.springboot.poster.controller;

import com.springboot.comment.entity.Comment;
import com.springboot.poster.repository.PostRepository;
import com.springboot.response.MultiResponseDto;
import com.springboot.response.SingleResponseDto;
import com.springboot.member.service.MemberService;
import com.springboot.poster.dto.PosterPatchDto;
import com.springboot.poster.dto.PosterPostDto;
import com.springboot.poster.entity.Poster;
import com.springboot.poster.mapper.PosterMapper;
import com.springboot.poster.service.PosterService;
import com.springboot.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

//컨트롤러에는 Rest컨트롤러, 요청 맵핑
@RestController
@RequestMapping("/v11/posters")
@Slf4j
//컨트롤러에서는 보내주고 받고 를 해야하니 서비스와 맵퍼를 받는다.
public class PosterController {
    private static final String POSTER_DEFAULT_URL = "/v11/posters";
    private final PosterService posterService;
    private final PosterMapper mapper;
    private final MemberService memberService;
    private final PostRepository postRepository;

    public PosterController(PosterService posterService, PosterMapper mapper, MemberService memberService, PostRepository postRepository) {
        this.posterService = posterService;
        this.mapper = mapper;
        this.memberService = memberService;
        this.postRepository = postRepository;
    }
    @PostMapping
    public ResponseEntity PostPoster (@Valid @RequestBody PosterPostDto posterPostDto) {
//            Poster poster = mapper.posterPostDtoToPoster(posterPostDto);
//
//            Member member = new Member();
//            member.setMemberId(posterPostDto.getMemberId());
//            poster.setMember(member);

            Poster savedPoster = posterService.createPoster(mapper.posterPostDtoToPoster(posterPostDto));
        URI location = UriCreator.createUri(POSTER_DEFAULT_URL, savedPoster.getPosterId());
        return ResponseEntity.created(location).build();

    }

    @PatchMapping("/{poster-id}")
    public ResponseEntity patchPoster (@PathVariable("poster-id") @Positive long posterId,
                                       @Valid @RequestBody PosterPatchDto posterPatchDto){
        posterPatchDto.setPosterId(posterId);
        Poster poster   = posterService.updatePoster(mapper.posterPatchDtoToPoster(posterPatchDto));
        //서비스에서 생성 그 생성하는 걸 mapper에서 가져와야한다
        //이제 업데이트 값 보내줘야한다

        return new ResponseEntity<>( new SingleResponseDto<>(mapper.postToPosterResponseDto(poster)), HttpStatus.OK);

    }

    @GetMapping("/{poster-id}")
    public ResponseEntity getPoster (@PathVariable ("poster-id") @Positive long posterId) {
        Poster poster = posterService.findPoster(posterId);
        poster.setView(poster.getView() +1 );
        postRepository.save(poster);
        return  new ResponseEntity(
                new SingleResponseDto<>(mapper.postToPosterResponseDto(poster) ),HttpStatus.OK

        );
    }


    @GetMapping
    public ResponseEntity getPosters(@RequestParam @Positive int page, @RequestParam @Positive int size) {
        Page<Poster> pagePosters = this.posterService.findPosters(page - 1, size);
        List<Poster> posters = pagePosters.getContent();
        return new ResponseEntity(new MultiResponseDto(this.mapper.postersToPosterResponses(posters), pagePosters), HttpStatus.OK);
    }

    @DeleteMapping("/{poster-id}")
    public ResponseEntity deletePoster (@PathVariable("poster-id") @Positive long posterId) {
        posterService.deletePoster(posterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/posts/sortByLikeCountDESC")
    public Page<Poster> sortByLikeCount (
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "10") int size ) {
        return posterService.sortByView(page , size);
    }




}
