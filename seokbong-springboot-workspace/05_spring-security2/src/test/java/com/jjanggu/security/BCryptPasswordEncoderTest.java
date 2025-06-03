package com.jjanggu.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BCryptPasswordEncoderTest {

    /*
        ## 암호화 ##
        1. 평문을 다른 사람들이 알아볼 수 없게 암호문으로 변경하는 과정
        2. DB 같은 저장소에 개인정보로 보호해야 되는 값들은 암호문으로 보관해야됨

                암호문
        평문 -----------> 암호문
        평문 <----------- 암호문
                복호화

        1) 양방향 암호화 방식 : 암호화, 복호화 둘 다 가능
        2) 단방향 암호화 방식 : 암호화만 가능

        옛날 : 양방향 암호화 방식 많이 사용 => 비밀번호 찾기를 통해 기존 비밀번호 제공
               단, 보안에 취약할 수 있었음  => 보안정책이 바뀜

        현재 : 단방향 암호화 방식 많이 사용 => 비밀번호 찾기 하면 비밀번호 변경 유도
               * SHA 암호화 방식 : 해시 알고리즘으로 구성된 암호화 방식
                                   암호문을 통해 평문 유추 불가
                                   단점 : 동일한 평문은 항상 동일한 암호문이 만들어짐
                                   ex) 1111 => xdf231@sdfwevsdf
                                       1111 => xdf231@sdfwevsdf
               * BCrypt 암호화 방식 : 솔팅(salting) 기법이 추가된 암호화 방식
                                      평문에 매번 랜덤값을 덧붙여 암호화를 진행 => 동일한 평문이여도 다른 암호문 나옴
                                      ex) 1111 + 1234 => xdf231@sdfwevsdf
                                          1111 + 1234 => 12334@sdfwecsdf
     */



    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 평문_to_암호문_테스트(){
        String rawPassword = "pass02";    // 평문
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("평문: " + rawPassword );
        System.out.println("암호문:" + encodedPassword);
    }
}
