package com.springboot.poster.entity;

import com.springboot.comment.entity.Comment;
import com.springboot.like.entity.Like;
import com.springboot.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

//게시물
@Getter
@Setter
@NoArgsConstructor
@Entity (name = "POSTERS")
public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long posterId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int view = 0 ;


    //등록날짜
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();


    @Column(nullable = false) //column 은 엔티티 필드값에 다 붙여줘야한다.
    private LocalDateTime modifyDate = LocalDateTime.now();


    @Enumerated(value = EnumType.STRING) //문자열 자체가 저장되는 것
    @Column(length = 20 , nullable = false)
    private PostStatus posterStatus = PostStatus.QUESTION_REGISTERED;

    @Enumerated(EnumType.STRING)
    @Column( length = 20, nullable = false) // nullable = true로 설정
    private QuestionStatus questionStatus = QuestionStatus.PUBLIC;


    //Member와 연결
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="MEMBER_ID")
    private Member member;

    public void addMember(Member member){
        this.member = member;
    }


    @OneToMany(mappedBy = "poster")
    private List<Comment> comments = new ArrayList<>();
    public void setComment(Comment comment) {
        comments.add(comment);
        if (comment.getPoster() != this)
            comment.setPoster(this);
    }

    @OneToMany (mappedBy = "poster" )
    private List<Like> likes = new ArrayList<>();
    public void setLike(Like like){
        likes.add(like);
        if (like.getPoster() != this)
            like.setPoster(this);
    }




    //enum 타입으로 posttatus 관리하고 .. 글 공개 비공개도 관리할 수 있다.
    public enum PostStatus {
        QUESTION_REGISTERED("질문 등록 상태"),
        QUESTION_ANSWERED("답변 완료 상태"),
        QUESTION_DELETED("질문 삭제 상태"),
        QUESTION_DEACTIVATE("질문 비활성화 상태");

        @Getter
        private String status;

        PostStatus(String status) {
            this.status = status;
        }  //생성자 만들기
    }

    public enum QuestionStatus {
        PUBLIC("공개글 상태"),
        SECRET("비밀글 상태");

        @Getter
        private String questionStatus;

        QuestionStatus(String questionStatus) {
            this.questionStatus = questionStatus;
        }
    }




}
