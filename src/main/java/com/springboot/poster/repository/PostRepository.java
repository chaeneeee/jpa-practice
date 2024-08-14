package com.springboot.poster.repository;

import com.springboot.like.entity.Like;
import com.springboot.poster.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//추상메서드
public interface PostRepository extends JpaRepository<Poster ,Long > {


}
