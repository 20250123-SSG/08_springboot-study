package com.ino.app;
/*
    MockMVC Test
    서버 띄우지 않고 Spring MVC의 요청-응답 흐름을 시뮬레이션 할 수 있는 테스트 도구
    웹 환경을 모방해서 테스트 수행
    HTTP 요청을 가상으로 보내고 controller-service-Mapper 까지 실제 동작 검증 가능
    각 계층의 연결 및 원하는 결과 도출 확인 가능
    주 메소드
     1. perform() : 요청 시뮬레이션,MockMvcRequestBuilder 객체를 통한 HTTP 요청 보냄
     2. andExpect() : 응답 검증, 응답 상태 코드/응답 본문 등 검증 메소드
     3. andDo() : 요청-응답 결과를 콘솔에 출력하여 확인하는 메소드
     4. andReturn() : 요청-응답 결과를 MvcResult 객체로 반환하는 메소드
 */

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.thymeleaf.spring6.expression.Mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // mockmvc 만들어줌
public class WebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login_fail_test1() throws Exception{
        // post /user/signin.do req - param(userId=input, userPwd=input)
        MvcResult mvcResult = mockMvc.perform(post("/user/signin.do")
                        .param("userId","sadsafa")
                        .param("userPwd", "asdf"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
        System.out.println("=============================");
        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));
    }

    @Test
    public void login_success_test1() throws Exception{
        // post /user/signin.do req - param(userId=input, userPwd=input)
        MvcResult mvcResult = mockMvc.perform(post("/user/signin.do")
                        .param("userId","user01")
                        .param("userPwd", "pass01"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
        System.out.println("=============================");
        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));
    }

    @Test
    public void signup_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform( post("/user/signup.do")
                .param("userId", "mockTest")
                .param("userPwd", "mockTest")
                .param("userName", "mockTest")
                .param("email","mock@gmail.com")
                .param("phone", "010-2222-3333")
                .param("gender","M") )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));
    }

    @Test
    public void idcheck_test() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/user/idcheck.do").param("checkId", "user01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("USED");
    }
}
