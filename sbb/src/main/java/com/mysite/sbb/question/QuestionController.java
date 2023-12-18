package com.mysite.sbb.question;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//생성자 포함
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;
    @GetMapping("/question/list")
    public String list(Model model){
        List<Question> questionList=this.questionRepository.findAll();
        //모델 객체는 자바클래스와 템플릿 간의 연결고리 역할을 함
        model.addAttribute("questionLIst", questionList);
        return "question_list";
    }
}