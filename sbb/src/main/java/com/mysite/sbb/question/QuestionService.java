package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.desktop.QuitEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
//서비스: 데이터 처리를 위해 작성하는 클래스
//건트롤러와 리포지터리의 중간자적인 입장에서 엔티티 객체와 DTO객체를 서로 변환하여 양방향에 전달하는 역할
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    //질문 목록을 조회하여 리턴하는 메서드
//    public List<Question>getList(){
//        return this.questionRepository.findAll();
//    }

    public Question getQuestion(Integer id){
        Optional<Question>question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateData(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page, String kw){
        //작성 날짜 순서대로 정렬
        List<Sort.Order>sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createData"));
        //한페이지에 10개씩 보여주겠다->해당 페이지의 데이터만 조회하도록 쿼리가 변경됨
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec,pageable);
        //쿼리문으로 검색하기
//        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    //kw(키워드)를 입력받아 쿼리의 조인문과 where문을 Specification 객체로 생성하여 리턴하는 메서드
    private Specification<Question> search(String kw){
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            //q:root자료형으로 기준이 되는 question 엔티티의 객체, 질문 제목과 내용을 검색하기 위해 필요
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //중복 제거
                query.distinct(true);
                //u1: question과 siteuser엔티티의 합집합에서 질문 작성자를 검색
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                //a: question과 answer엔티티의 합집합에서 답변 내용 검색
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                //u2: a객체와 siteuser엔티티의 합집합에서 답변 작성자 검색
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);

                //or:여러 조건 중 하나라도 만족하면 됨
                return cb.or(cb.like(q.get("subject"), "%"+kw+"%")//제목
                        ,cb.like(q.get("content"), "%"+kw+"%")//내용
                        ,cb.like(u1.get("username"), "%"+kw+"%")//질문 작성자
                        ,cb.like(a.get("content"), "%"+kw+"%")//답변 내용
                        ,cb.like(u2.get("username"), "%"+kw+"%")//답변 작성자
                        );
            }
        };
    }

}
