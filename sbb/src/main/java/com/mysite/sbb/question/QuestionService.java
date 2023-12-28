package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public void create(String subject, String content){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateData(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page){
        //작성 날짜 순서대로 정렬
        List<Sort.Order>sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createData"));
        //한페이지에 10개씩 보여주겠다->해당 페이지의 데이터만 조회하도록 쿼리가 변경됨
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

}
