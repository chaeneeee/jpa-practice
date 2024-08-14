package com.springboot.member.repository;

import com.springboot.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//repository 는 데이터베이스와 상호작용하는 애들 JpaRepository를 확장하여 사용<
// Member 의 CRUD(Create, Read, Update, Delete) 작업을 수행하는 메서드들을 제공
//JpaRepository 안애 < > 으로 t 랑 Id를 받고 있다 .
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail (String email);
//리포지토리는 데이터와 소통하는 애 그래서 jpa리포지토리 안에 구현되어있는 애들 다 상속받아 사용할 수 있다.
//찾고자 하는 멤버 이메일이 없을 수 있다 그 때 발생하는 널포인트입셉션 방지하기 위해 옵셔널로 받는다 .
    // findBy와 Email을 분석하여 "email" 필드를 기반으로 멤버를 찾는 쿼리를 생성
// 이메일 같은 거 있는 지 비교하기 위해 만든 것 verify 예외처리를 위헤 구현한 것
}
