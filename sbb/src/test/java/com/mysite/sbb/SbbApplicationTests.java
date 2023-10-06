package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	//스프링의 DI(스프링이 객체를 대신 생성하여 주입한다)기능-questionRepository 객체를 스프링이 자동으로 생성해줌
	private QuestionRepository questionRepository;

	@Test
	void TestJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고싶습니다");
		q1.setCreateData(LocalDateTime.now());
		this.questionRepository.save(q1); //첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateData(LocalDateTime.now());
		this.questionRepository.save(q2); //두번째 질문 저장
	}

}
