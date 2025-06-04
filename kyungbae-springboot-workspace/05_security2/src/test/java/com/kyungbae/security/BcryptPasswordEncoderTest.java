package com.kyungbae.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BcryptPasswordEncoderTest {

    /*
     ## 암호화
     1. 평문을 다른 사람들이 알아볼 수 없게 암호문으로 변경하는 과정
     2. DB 같은 저장소에 개인정보로 보호해야 되는 값들은 암호문으로 본관해야됨

                암호화
     평문 -------------------> 암호문
     평문 <------------------- 암호문
                복호화

     1) 양방향 암호화 방식 : 암호화, 복호화 둘 다 가능
     2) 단방향 암호화 방식 : 암호화만 가능
     옛날 : 양발향 암호화 방식 많이 사용 => 비밀번호 찾기를 통해 기존 비밀번호 제공
            단, 보안에 취약할 수 있었음 => 보안 정책 변경
     현재 : 단방향 암호화 방식 많이 사용 => 비밀번호 찾기 하면 비밀번호 변경 유도
            * SHA 암호화 방식 : 해시 알고리즘으로 구성된 암호화 방식
                                암호문을 통해 평문 유추 불가
                                단점 : 동일한 평문은 항상 동일한 암호문이 만들어짐
                                ex) 1111 => xd34234@oiewjflkwfe
                                    1111 => xd34234@oiewjflkwfe
     */

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 평문_to_암호문_테스트(){
        String rawPassword = "admin";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("평문: " + rawPassword);
        System.out.println("암호문: " + encodedPassword);
    }

}
