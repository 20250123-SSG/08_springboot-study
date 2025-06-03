package com.younggalee.security2.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BCryptPasswordEncoderTest {
    /*
        ## 암호화
        DB같은 저장소에 개인정보로 보호해야되는 값들은 암호문으로 보관해야함 (법)
        > 암호화
        < 복호화
        1) 양방향 암호화 방식 : 암호화, 복호화 둘다 가능 (암호문을 통해 평문을 유추할 수 있음 (키가 있다면))
        2) 단방향 암호화 방식 : 암호화만 가능. 복호화 불가능

        기존에는 양방향 암호화 방식 많이 사용 > (비밀번호 찾기 시, 비밀번호 알려줬음). // 보안에 취약.
        요즘에는 단방향 암호화 방식 사용 > 비밀번호 찾기 시, 변경으로 넘어감 (비번 알 수 없음) // 보안정책변경됨
               * SHA 암호화 방식 : 해시 알고리즘으로 구성된 암호화 방식
                                 > 암호문을 통해 평문 유추 불가 (하지만, 동일한 평문은 항상 동일한 암호문이 만들어진다는 단점이 있음)
                                                          > 반복적으로 샘플을 학습해보면 역순으로 평문 유추가능해짐....
               * BCrypt 암호화 방식 : salting 기법이 추가된 암호화 방식
                                   평문에 매번 랜덤값을 덧붙여 암호화를 진행 >> 동일한 평문이어도 다른 암호문 나옴
                                   - Spring security는 해당 방식을 제공함
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 평문_to_암호문_테스트() {
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println(rawPassword + " / " + encodedPassword);
    }
}
