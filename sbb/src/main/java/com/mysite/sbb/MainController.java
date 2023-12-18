package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    //url 요청에 대한 응답으로 문자열을 리턴하라는 의미
    @ResponseBody
    public String index(){
        //return 값이 없을 시 오류 발생
        return "안녕하세요 sbb에 오신것을 환영합니다";
    }

    @GetMapping("/")
    public String root(){
        //redirect: 완전히 새로운 URL로 요청
        //forward: 기존 요청 값들이 유지된 상태료 URL전환
        return "redirect:question/list";
    }
}
