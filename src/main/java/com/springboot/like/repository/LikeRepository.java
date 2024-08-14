package com.springboot.like.repository;

import com.springboot.like.entity.Like;
import com.springboot.member.entity.Member;
import com.springboot.poster.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like , Long> {
    Optional<Like> findAllByMemberAndPoster (Member member, Poster poster);
}  //그 member 랑 poster 에 like 가 있는지 없는지를 확인하는 것
