package com.jjanggu.app;

/*
    ## MockMvc 테스트 ##
    1. 실제 서버를 띄우지 않고 Spring MVC의 요청-응답 흐름을 시뮬레이션 할 수 있는 테스트 도구
    2. 실제 웹 환경을 모방(Mock)해서 테스트를 수행함
    3. HTTP 요청을 가상으로 보내고 Controller->Service->Mapper 까지의 실제 동작을 검증할 수 있음
       즉, 각 계층이 잘 연결되어있는지, 원하는 결과가 도출되는지를 확인해볼 수 있음
    4. 주요 메소드
       1) perform()   : 요청 시뮬레이션, MockMvcRequestBuilders 객체를 통해 HTTP 요청을 보내는 메소드
       2) andExpect() : 응답 검증, 응답 상태 코드/응답 본문 등 검증하는 메소드
       3) andDo()     : 요청-응답 결과를 콘솔에 출력하여 확인하는 메소드
       4) andReturn   : 요청-응답 결과를 MvcResult 객체로 반환하는 메소드
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 로그인_실패_테스트1() throws Exception {

        // post방식 /user/signin.do 요청 - param(userId=입력값, userPwd=입력값)
        MvcResult mvcResult = mockMvc.perform( post("/user/signin.do")        // 요청 url, 요청 전송방식
                                              .param("userId", "sadfasdf")    // 요청 파라미터
                                              .param("userPwd", "sdfsdf") )
                                      .andDo( print() )                         //요청-응답 흐름 출력
                                      .andExpect( status().is3xxRedirection() ) // 응답코드가 302(Redirect)
                                      .andReturn();

        System.out.println("==================================");
        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));

    }

    @Test
    public void 로그인_실패_테스트2() throws Exception {

        // post방식 /user/signin.do 요청 - param(userId=입력값, userPwd=입력값)
        MvcResult mvcResult = mockMvc.perform( post("/user/signin.do")        // 요청 url, 요청 전송방식
                                              .param("userId", "user01")    // 요청 파라미터
                                              .param("userPwd", "sdfsdf") )
                                      .andDo( print() )
                                      .andExpect( status().is3xxRedirection() )
                                      .andReturn();

        System.out.println("==================================");
        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));

    }

    @Test
    public void 로그인_성공_테스트() throws Exception {

        // post방식 /user/signin.do 요청 - param(userId=입력값, userPwd=입력값)
        MvcResult mvcResult = mockMvc.perform( post("/user/signin.do")        // 요청 url, 요청 전송방식
                        .param("userId", "user01")    // 요청 파라미터
                        .param("userPwd", "pass01") )
                .andDo( print() )
                .andExpect( status().is3xxRedirection() )
                .andReturn();

        System.out.println("==================================");
        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));

    }

    @Test
    public void 회원가입_테스트() throws Exception{
        MvcResult mvcResult = mockMvc.perform( post("/user/signup.do")
                                              .param("userId", "mockTest")
                                              .param("userPwd", "mockTest")
                                              .param("userName", "목테스트")
                                              .param("email", "mock@gmail.com")
                                              .param("phone", "010-2222-3333")
                                              .param("gender", "M"))
                                     .andDo( print() )
                                     .andExpect( status().is3xxRedirection() )
                                     .andReturn();

        System.out.println(mvcResult.getModelAndView().getViewName());
        System.out.println(mvcResult.getFlashMap().get("message"));
    }

    @Test
    public void 아이디중복체크_테스트() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/user/idcheck.do").param("checkId","user01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("USED");
    }
}
