package com.springboot.member.dto;


import com.springboot.comment.entity.Comment;
import com.springboot.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

//MemberResponseDto는 엔티티에서 필요한 정보 추출해서 클라이언트에서 응답할 때 사용하는 것
//멥퍼에서 변환한 dto 값을 담기위한 반환객체
//패치나 get 으로 클라이언트에게 보여줄 정보 전달할 때 쓰는 것
@Getter
@AllArgsConstructor //생성자 자동 생성
public class MemberResponseDto {
 private Long memberId;
 private  String name;
 private  String phoneNumber;
 private String email;
 private Member.MemberStatus memberStatus;

 public String getMemberStatus() {
  return memberStatus.getStatus();
 }
}
