package com.mysite.sbb.question;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//생성자 포함
@RequiredArgsConstructor
@Controller
public class QuestionController {

    //questionService 객체는 생성자 방식을 DI규칙에 의해 주입됨
    private final QuestionService questionService;
    @GetMapping("/question/list")
    public String list(Model model){
        List<Question> questionList=this.questionService.getList();
        //모델 객체는 자바클래스와 템플릿 간의 연결고리 역할을 함
        model.addAttribute("questionLIst", questionList);
        return "question_list";
    }

    @GetMapping(value="/question/detail/{id}")
                                      //PathVariable: 변하는 id값을 얻을 때
    public String detail(Model model, @PathVariable("id")Integer id){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
