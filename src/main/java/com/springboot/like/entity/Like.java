package com.springboot.like.entity;

import com.springboot.member.entity.Member;
import com.springboot.poster.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.ArrayList;
import java.util.List;

@Entity (name ="LIKES")
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likeId;
    
    @ManyToOne
    @JoinColumn( name = "POSTER_ID")
    private Poster poster;
    public void setPoster(Poster poster){
        this.poster = poster;
        if (!poster.getLikes().contains(this)){
            poster.setLike(this);
        }
    }


    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;





}
