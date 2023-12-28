package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {
//
//	@Autowired
//	//스프링의 DI(스프링이 객체를 대신 생성하여 주입한다)기능-questionRepository 객체를 스프링이 자동으로 생성해줌
//	private QuestionRepository questionRepository;
//
//	@Autowired
//	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

//	@Transactional
//	//메서드가 종료될때까지 DB세션 유지
	//transactional->메서드를 하나의 트랜잭션으로 묶게됨 -> 이 메서드가 끝나야 db세션이 종료되는 것임
	@Test
	void TestJpa() {
		//데이터 저장하기
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고싶습니다");
//		q1.setCreateData(LocalDateTime.now());
//		this.questionRepository.save(q1); //첫번째 질문 저장
//
//		Question q2 = new Question();
//		q2.setSubject("스프링 부트 모델 질문입니다");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateData(LocalDateTime.now());
//		this.questionRepository.save(q2); //두번째 질문 저장

		//findAll
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(6, all.size());
//
//		Question q = all.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());

		//findById
//		Optional<Question> oq = this.questionRepository.findById(5);
//		if(oq.isPresent()){
//			Question q = oq.get();
//			assertEquals("sbb가 무엇인가요?", q.getSubject());
//		}

		//findBySubject
//		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
//		assertEquals(13, q.getId());

		//findBySubjectAndContent
//		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고싶습니다");
//		assertEquals(13, q.getId());

		//findBySubjectLike
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		//sbb%-sbb로 시작하는 문자열
//		Question q = qList.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());

		//데이터 수정
//		Optional<Question> oq =this.questionRepository.findById(13);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);

		//데이터 삭제
//		assertEquals(2, this.questionRepository.count());
//		//count는 해당 리포지토리의 총 데이터건수를 리턴함
//		Optional<Question> oq = this.questionRepository.findById(13);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q);
//		assertEquals(1, this.questionRepository.count());

		//답변 저장하기
//		Optional<Question> oq = this.questionRepository.findById(14);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//
//		Answer a = new Answer();
//		a.setContent("네 자동으로 생성됩니다");
//		a.setQuestion(q); //어떤 질문의 답변인지 알기 위해서
//		a.setCreateData(LocalDateTime.now());
//		this.answerRepository.save(a);

		//답변 조회하기
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(14, a.getQuestion().getId());

		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}


	}

}
