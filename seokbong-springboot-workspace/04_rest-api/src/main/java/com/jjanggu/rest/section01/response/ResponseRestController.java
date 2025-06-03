package com.jjanggu.rest.section01.response;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Hidden
/*
    ## @RestController ##
    1. @Controller와 @ResponseBody를 합친 어노테이션
    2. 클래스 레벨에 작성, 해당 클래스 내의 모든 핸들러메소드에 @ResponseBody가 묵시적으로 적용됨
 */
@RequestMapping("/response")
@RestController
public class ResponseRestController {

    // 1. 문자열 자원 응답
    @GetMapping("/text")
    public String getText(){
        return "hello";
    }

    // 2. 숫자 자원 응답
    @GetMapping("/number")
    public int getNumber(){
        return (int)(Math.random() * 10 + 1);
    }

    // 3. Object 자원 응답
    @GetMapping("/message")
    public Message getMessage(){
        return new Message(200, "메세지내용");
    }

    // 4. List 자원 응답
    @GetMapping("/list")
    public List<String> getList(){
        return List.of("봄","여름","가을","겨울");
    }

    // 5. Map 자원 응답
    @GetMapping("/map")
    public Map<Integer, String> getMap(){
        return Map.of(200, "정상응답"
                    , 400, "잘못된요청"
                    , 500, "개발자의실수");
    }

    // 6. Image 자원 응답
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/static/heart2.png").readAllBytes();
    }

    // POSTMAN

}
