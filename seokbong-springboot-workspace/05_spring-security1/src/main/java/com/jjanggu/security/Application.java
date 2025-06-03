package com.jjanggu.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class}) // SpringSecurity를 임시적으로 꺼둠
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
