package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    //null값 또는 빈 문자열 허용하지 않음
    @NotEmpty(message="제목은 필수사항입니다")
    //최대길이 200바이트
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수사항입니다")
    private String content;
}
