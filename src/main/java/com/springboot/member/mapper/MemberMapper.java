package com.springboot.member.mapper;

import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.dto.MemberResponseDto;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
//맵퍼에는 맵퍼 plus , 의존성 주입
//componentModel 스프링 의존성 주입 ,
@Mapper(componentModel = "spring" )  //맵퍼는 맵퍼 붙이기
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
   // MemberPostDto 를 Member 로 반환
   Member memberToPatchDtoToMember (MemberPatchDto memberPatchDto);

    MemberResponseDto memberToMemberResponseDto(Member member);
    //얘는 반환용 즉 엔티티 Member 값을 받아 Dto 로 반환한다. dto 를 담는 객체
    List<MemberResponseDto> membersToMemberResponses(List<Member> members);

  //delete 는 보내줄 거 없으니까 그냥 멤버아이디 받아서 삭제하고 끝
}
add