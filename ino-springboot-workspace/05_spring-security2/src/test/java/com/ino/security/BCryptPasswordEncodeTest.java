package com.ino.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BCryptPasswordEncodeTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void encryptTest(){
        String rawPwd = "pass02";
        String enecodedPwd = passwordEncoder.encode(rawPwd);
        System.out.println("Encoded Pwd : " + enecodedPwd);
    }
}
