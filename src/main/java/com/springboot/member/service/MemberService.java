package com.springboot.member.service;

//crud 만들기

import com.springboot.auth.utils.CustomBeanUtils;
import com.springboot.auth.utils.JwtAuthorityUtils;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import com.springboot.poster.entity.Poster;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.sound.midi.MetaMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final JwtAuthorityUtils jwtAuthorityUtils;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder,
                         ApplicationEventPublisher publisher,
                         JwtAuthorityUtils authorityUtils,
                         CustomBeanUtils customBeanUtils,
                         JwtAuthorityUtils jwtAuthorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;

        this.jwtAuthorityUtils = jwtAuthorityUtils;
    }

    //encryptedPassword 암호화된 비밀번호
    //멤버 만들 때 메일받고, 비밀번호 encode password 받아와서 encode, Role 저장
    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword()); //패스워드를 가져와서 인코딩
        member.setPassword(encryptedPassword);
        List<String> roles = jwtAuthorityUtils.createRole(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member updateMember (Member member) {
     Member UpdateMember =findVerifiedMember(member.getMemberId());
        Optional.ofNullable(member.getName()).ifPresent(name -> UpdateMember.setName(name));
        Optional.ofNullable(member.getPhoneNumber()).ifPresent(phoneNumber -> UpdateMember.setPhoneNumber(phoneNumber));
        Optional.ofNullable(member.getMemberStatus()).ifPresent(memberStatus -> UpdateMember.setMemberStatus(memberStatus));
         UpdateMember.setModifyDate(LocalDateTime.now()); //얘는 뭐가 수정되던 무조건 바뀌어야하는 거니까
        return  memberRepository.save(UpdateMember);
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId); //멤버 있는지 없는지 확인하는 메서드
    }
    //페이지네이션 적용해서
    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page,size, Sort.by("memberId").descending()));

    }
    public void deleteMember(long memberId) {
        Member deleteMember = findVerifiedMember(memberId);
        memberRepository.delete(deleteMember);  //멤버 리포지토리에 jpa 로 구현되어있는 delete 쓰는 것
        //save 처럼
    }
    //이메일 존재 확인  boolean 타입으로 받아서 존재하는지 없는지를 확인
    public void verifyExistEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }//isPresent 옵셔널 객체가 존재하느냐 않느냐 존재하면 true 로 있다고 나올때만
        //get 으로 가져올 수 있긴하다.
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Member findVerifiedMember (long memberId) {
        Optional<Member> OptionalMember = memberRepository.findById(memberId);
        Member findMember =
                //멤버가 없으면 비지니스 입셉션 날린다.
            OptionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
   }
}
