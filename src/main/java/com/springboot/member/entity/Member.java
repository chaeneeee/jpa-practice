package com.springboot.member.entity;


import com.springboot.like.entity.Like;
import com.springboot.member.service.MemberService;
import com.springboot.poster.entity.Poster;
import lombok.*;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.mapstruct.ObjectFactory;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

@NoArgsConstructor //파라미터가 없는 디폴트 생성자를 자동으로 생성
@Getter
@Setter
@Entity  //엔티티 만들 때 자꾸 엔티티 에너테이션 빼트리지 말기
 public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 늘리면서 생성 IDENTITY 가 자동생성
    private Long memberId;
    //Long 참조타입 초기값 null . long 이 원시타입  초기값 0
    @Column( length = 100 , nullable = false)
    private String name;

   @Column( length = 15 , nullable = false , unique = true)
    private String phoneNumber;

   @Column(nullable = false , unique = true , updatable = false)
    private String email;
   //이넘타입에 적용되는 에노테이션  EnumType.STRING을 사용하여 Enum 상수를 문자열로 데이터베이스에 저장
   @Enumerated(value = EnumType.STRING)
   @Column(length = 20 , nullable = false)
   private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

   //패스워드 추가
   @Column (length = 100 , nullable = false)
   private String password;
   //역할 추가

    @ElementCollection(fetch = FetchType.EAGER) //roles 를 데이터베이스에 저장할 때 쓰이는 에노테이션
    private List<String> roles = new ArrayList<>();
    //이 리스트트 따로따로 저장해줘 라고 하는 것
    //이 에노테이션으로 사용자 등록 시 사용자의 권한을 등록하기 위한 테이블을 만들 수 있다 .


//이메일은 바꾸면 안된다 unique 한 값

    //생성날짜
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime modifyDate =  LocalDateTime.now();

    //cascade = CascadeType.PERSIST 는 영속성 전이로 자식들도 자동으로 영속화
    @OneToMany(mappedBy = "member" , cascade = CascadeType.PERSIST)
    private List<Poster> posters = new ArrayList<>();
    public void  setLike(Poster poster) {
        posters.add(poster);
        if (poster.getMember() != this)
            poster.setMember(this);
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Like> likes = new ArrayList<>();

    //enum은 열거형이라 이렇게 쓴다.
    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;
        MemberStatus(String status) {this.status = status;}
            //메서드 호출
    }


   }



