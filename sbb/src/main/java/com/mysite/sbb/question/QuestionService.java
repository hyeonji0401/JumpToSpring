package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<Question>getList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question>question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else{
            throw new DataNotFoundException("question nod found");
        }
    }

}
