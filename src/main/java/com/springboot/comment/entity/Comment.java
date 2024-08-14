package com.springboot.comment.entity;

import com.springboot.member.entity.Member;
import com.springboot.poster.entity.Poster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity (name = "COMMENTS")
@NoArgsConstructor
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long commentId;

    @Column(nullable = false, length = 500)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @LastModifiedBy
    private LocalDateTime modifyDate = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.PUBLIC;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    @ManyToOne
    @JoinColumn(name = "POSTER_ID")
    private Poster poster;
    public void setPoster(Poster poster){
        this.poster = poster;
        if (!poster.getComments().contains(this)){
            poster.setComment(this);
}
    }





    public enum CommentStatus {
        PUBLIC("질문 답변 대기"),
        SECRET("질문 답변 완료 ");


        @Getter
        private String stepDescription;

        CommentStatus(String stepDescription) {
            this.stepDescription = stepDescription;
        }


    }



//멤버아이디 보드아이디 넣어주기
    //상태랑 생성날짜는 나중에

}
