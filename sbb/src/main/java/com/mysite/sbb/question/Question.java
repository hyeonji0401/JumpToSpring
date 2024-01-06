package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
//데이터베이스 테이블과 매핑되는 자바 클래스를 말함
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //generatedValue-자동으로 1씩 증가
    //strategy-고유번호 생성
    //generationType.IDENTITY-해당 컬럼만의 독립적인 시퀀스를 생성하여 번호를 증가시킴
    private Integer id;

    @Column(length =200)
    //length-컬럼의 길이 설정
    private String subject;

    @Column(columnDefinition = "TEXT")
    //columnDefinition-컬럼 속성 정의
    //TEXT-글자 수를 제한 할 수 없는 경우
    private String content;

    private LocalDateTime createData;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    //mappedBy-참조 엔티티의 속성명(여기서는 answer엔티티에서 question 엔티티를 참조했다는 말임)
    //casacedType.REMOVE-질문을 삭제하면 그에 달린 답변들도 모두 함꼐 삭제
    private List<Answer> answerList;

    //사용자 한 명이 여러 질문 작성 가능
    @ManyToOne
    private SiteUser author;
}
