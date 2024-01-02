package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//스프링의 환경설정 파일임을 의미함
//Bean을 등록할때 싱글톤이 되도록 보장함
//스프링 컨테이너에서 Bean을 관리할 수 있게된다
@Configuration
//모든 요청 URL이 스프링 시큐리티의 제어를 받도록 함
@EnableWebSecurity
public class SecurityConfig {
    //스프링 컨테이너에 의해 관리되는 재사용가능한 소프트웨어 컴포넌트(자바 객체)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //모든 인증되지 않은 요청을 허락한다는 의미
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()
                        .and()
                //CSRF(웹사이트 취약점 공격을 방지 기술로 토큰값을 세션을 통해 발생하고 웹페이지에서는 폼 전송 시 해당 토큰을 함께 전송하여 실제 웹헤이지에서 작성된 데이터가 전달되었는지 검증)
                //H2콘솔은 CSRF토큰을 발행하는 기능이 없기 때문에 예외처리를 함
                .csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .and()
                //H2 콘솔의 화면이 Frame구조로 작성되어있음, 스프링 시큐리티는 X-Frame-options헤더값을 사용하여 사이트의 콘텐츠가 다른 사이트에 포함되지 않도록 함(ClickJacking 공격 막음)
                //sameOrigin: frame에 포함된 페이지가 페이지를 제공하는 사이트와 동일할 경우 오류 발생하지 않음
                .headers()
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        return http.build();
    }
}
