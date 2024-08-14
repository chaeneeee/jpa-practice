package com.springboot.member.controller;

import com.springboot.response.MultiResponseDto;
import com.springboot.response.SingleResponseDto;
import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.entity.Member;
import com.springboot.member.mapper.MemberMapper;
import com.springboot.member.service.MemberService;
import com.springboot.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

//컨트롤러는 전달해주는 것 직접적인 crud는 멤버 서비스에서 한다 .
@RestController //컨트롤러 만들기
@RequestMapping("/v11/members") //
@Validated //유효성검사 하고
@Slf4j  // 출력
public class MemberController {
    private static final String MEMBER_DEFAULT_URL = "/v11/members";

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }
    //dto 파라미터로 받아서 ResponseEntity 엔티티로 보내기
    //Valid 는 유효성 검사 RequestBody 요청의 본문(Body 를 객체로 변화시키는데 사용
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {

        Member postMember = memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, postMember.getMemberId());
        return ResponseEntity.created(location).build();
    }
    //몇 번 멤버아이디에 맵핑할 것인지
    @PatchMapping("/{member-id}")  //http 통신에서의 uri 경로    //URL 경로 변수에서 member-id 값을 가져와서 memberId라는 변수에 할당
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto) {
        memberPatchDto.setMemberId(memberId);

        Member member = memberService.updateMember(mapper.memberToPatchDtoToMember(memberPatchDto));
//SingleResponseDto 는 단일 객체를 포함하는  http 응답을 표현하기위한 dto
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)),
                HttpStatus.OK);
        //MemberDto.patch 에 대한 에노테이션인 것
    }

    //getmember 는 페이지 적용 x 어차피 하나니까
    @GetMapping("/{member-id}")
    public ResponseEntity getMember (@PathVariable ("member-id") @Positive long memberId ){
        Member member = memberService.findMember(memberId);
        return  new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)),HttpStatus.OK
        );
    }


    //멤버 정보를 페이지네이션 적용합니당
    @GetMapping
    public ResponseEntity getMembers (@Positive @RequestParam int page,
                                     @Positive @RequestParam int size ) {
        Page<Member> pageMembers = memberService.findMembers(  page - 1, size);
        List<Member> members    = pageMembers.getContent();
        return  new ResponseEntity<>(
                new MultiResponseDto<>( mapper.membersToMemberResponses(members), pageMembers),HttpStatus.OK);

    }
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember (
            @PathVariable("member-id") @Positive long memberId ) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        //성공적으로 삭제했다고 알려주기 위해
    }




}





