package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        //BCrypt 해싱함수를 사용해서 비밀번호 암호화
//      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //BCrypt 객체를 직접 생성하여 사용하지 않고 빈으로 등록한 PasswordEncoder 객체를 주입받아 사용
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
