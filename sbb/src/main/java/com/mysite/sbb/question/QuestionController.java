package com.mysite.sbb.question;


import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

//생성자 포함
@RequiredArgsConstructor
@Controller
public class QuestionController {

    //questionService 객체는 생성자 방식을 DI규칙에 의해 주입됨
    private final QuestionService questionService;
    private final UserService userService;

//    @GetMapping("/question/list")
//    public String list(Model model){
//        List<Question> questionList=this.questionService.getList();
//        //모델 객체는 자바클래스와 템플릿 간의 연결고리 역할을 함
//        model.addAttribute("questionList", questionList);
//        return "question_list";
//    }

    @GetMapping("/question/list")
    //page파라미터가 전달되지 않은 경우 디폴트 값 0으로 설정
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping(value="/question/detail/{id}")
                                      //PathVariable: 변하는 id값을 얻을 때
    public String detail(Model model, @PathVariable("id")Integer id, AnswerForm answerForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    //로그인 한 사람만 이 동작을 수행시킬 수 있다는 말
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }
    //메서드 오버로딩(매개변수 다른 경우 메서드명 공유)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/create")
    //@valid: 설정한 검증 기능 작동
    //BindingResult: 검증이 수행될 결과 객체, 반드시 @Valid 매개변수 뒤에 위치해야함
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list"; //질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal)
    {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        questionForm.setSubject(question.getSubject());
        question.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
}
