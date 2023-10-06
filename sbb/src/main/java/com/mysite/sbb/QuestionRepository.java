package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

//JapRepository<리포지터리 대상 엔티티 타입, 해당 엔티티 PK속성 타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
