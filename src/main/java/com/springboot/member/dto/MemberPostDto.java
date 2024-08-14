package com.springboot.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//Dto의 역할
//데이터전송, 유효성 검사, 응답포맷 정의
//전송뿐만아니라 유효성도 여기서 검사한다.
//데이터의 유효성을 검사하는 것
@AllArgsConstructor //아 전체 생성자 만들어주는거
@Getter  //멤버값 다른데 가져다 써야함

public class MemberPostDto {
    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank(message = "이름은 공백이 아니어야 합니다")
    private String name;

}
