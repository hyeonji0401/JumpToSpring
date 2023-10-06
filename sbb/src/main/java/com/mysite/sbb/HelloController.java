package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello") // "/hello" url과 hello 메서드를 매핑함
    //url과 메서드 명은 동일할 필요 없음
    @ResponseBody //메서드의 응답 결과가 문자열 그 자체 임을 나타냄
    public String hello(){
        return "Hello World";
    }

}
