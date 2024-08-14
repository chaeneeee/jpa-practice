package com.springboot.member.dto;

import com.springboot.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class MemberPatchDto {
    private long memberId; // 뭘 바꿔야하는지 알아야함

    private String name;

    private String phoneNumber;

    private Member.MemberStatus memberStatus;
    //멤버 아이디 연결해줘야함

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
   //this.memberId 는 위에 필드값의 this
        //위에 필드값의 memberId를 내가 파라미터로 받은 memberId로 바꾸는 거


}
